package com.example.miniproyecto1.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.miniproyecto1.R

class InventoryWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Llama a super.onUpdate (opcional)
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        // Actualiza cada widget activo
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.inventory_widget)

            // Ejemplo: establecer texto o imagen
            views.setTextViewText(R.id.widgetTitle, "Inventario")

            // Actualiza el widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

            InventoryWidget.updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}
