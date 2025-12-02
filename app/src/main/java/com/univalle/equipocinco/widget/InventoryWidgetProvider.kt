package com.univalle.equipocinco.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.equipocinco.R
import java.text.NumberFormat
import java.util.*

class InventoryWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val ACTION_TOGGLE_SALDO = "com.univalle.equipocinco.ACTION_TOGGLE_SALDO"
        private const val ACTION_OPEN_LOGIN = "com.univalle.equipocinco.ACTION_OPEN_LOGIN"
        private const val PREFS_NAME = "inventory_widget_prefs"
        private const val PREF_SHOW_SALDO = "show_saldo"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val component = ComponentName(context, InventoryWidgetProvider::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(component)

        when (intent.action) {
            ACTION_TOGGLE_SALDO -> {
                val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val current = prefs.getBoolean(PREF_SHOW_SALDO, false)
                prefs.edit().putBoolean(PREF_SHOW_SALDO, !current).apply()
                for (id in appWidgetIds) updateWidget(context, appWidgetManager, id)
            }

            ACTION_OPEN_LOGIN -> {
                val loginIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                loginIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(loginIntent)
            }
        }
    }

    private fun updateWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.inventory_widget)

        // Toggle eye icon
        val toggleIntent = Intent(context, InventoryWidgetProvider::class.java).setAction(ACTION_TOGGLE_SALDO)
        val togglePending = PendingIntent.getBroadcast(
            context, 0, toggleIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.img_eye, togglePending)

        // Manage inventory button
        val manageIntent = Intent(context, InventoryWidgetProvider::class.java).setAction(ACTION_OPEN_LOGIN)
        val managePending = PendingIntent.getBroadcast(
            context, 1, manageIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.ll_manage, managePending)
        views.setOnClickPendingIntent(R.id.img_manage, managePending)

        // Show/hide balance
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val showSaldo = prefs.getBoolean(PREF_SHOW_SALDO, false)

        if (showSaldo) {
            views.setImageViewResource(R.id.img_eye, R.drawable.ic_eye_closed)
            loadSaldoFromFirestore(context) { saldoFormateado ->
                views.setTextViewText(R.id.tv_saldo, saldoFormateado)
                manager.updateAppWidget(widgetId, views)
            }
        } else {
            views.setImageViewResource(R.id.img_eye, R.drawable.ic_eye_open)
            views.setTextViewText(R.id.tv_saldo, "$****")
            manager.updateAppWidget(widgetId, views)
        }
    }

    private fun loadSaldoFromFirestore(context: Context, callback: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        if (userId == null) {
            callback("$0.00")
            return
        }

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("users")
            .document(userId)
            .collection("products")
            .get()
            .addOnSuccessListener { snapshot ->
                val total = snapshot.documents.sumOf { doc ->
                    val price = doc.getDouble("price") ?: 0.0
                    val quantity = doc.getLong("quantity")?.toInt() ?: 0
                    price * quantity
                }
                val formatted = formatCurrency(total)
                callback(formatted)
            }
            .addOnFailureListener {
                callback("$0.00")
            }
    }

    private fun formatCurrency(value: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale("es", "CO"))
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2
        return "$" + nf.format(value)
    }
}