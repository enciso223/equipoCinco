package com.univalle.equipocinco.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.equipocinco.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 LOGS ANTES DE TODO
        Log.wtf(TAG, "========================================")
        Log.wtf(TAG, "🔥 MAINACTIVITY INICIADA")
        Log.wtf(TAG, "========================================")
        println("🔥 MAINACTIVITY INICIADA (println)")
        System.out.println("🔥 MAINACTIVITY INICIADA (System.out)")

        setContentView(R.layout.activity_main)

        Log.wtf(TAG, "✅ setContentView ejecutado")

        // Toast para confirmar que la app está corriendo
        Toast.makeText(this, "🔥 MainActivity Iniciada", Toast.LENGTH_LONG).show()

        // TEST DE FIREBASE
        testFirebase()
    }

    private fun testFirebase() {
        Log.wtf(TAG, "🧪 Iniciando testFirebase()")

        try {
            // Test 1: Firebase Auth
            Log.wtf(TAG, "Intentando obtener FirebaseAuth...")
            val auth = FirebaseAuth.getInstance()

            if (auth != null) {
                Log.wtf(TAG, "✅ Firebase Auth OK")
                Toast.makeText(this, "✅ Firebase Auth OK", Toast.LENGTH_SHORT).show()
            } else {
                Log.wtf(TAG, "❌ Firebase Auth es NULL")
                Toast.makeText(this, "❌ Firebase Auth NULL", Toast.LENGTH_LONG).show()
            }

            // Test 2: Firestore
            Log.wtf(TAG, "Intentando obtener Firestore...")
            val firestore = FirebaseFirestore.getInstance()

            if (firestore != null) {
                Log.wtf(TAG, "✅ Firestore inicializado")
                Toast.makeText(this, "🔄 Conectando a Firestore...", Toast.LENGTH_SHORT).show()

                // Test de conexión
                firestore.collection("test")
                    .get()
                    .addOnSuccessListener { result ->
                        Log.wtf(TAG, "✅ FIRESTORE CONECTADO!")
                        Log.wtf(TAG, "Documentos: ${result.size()}")
                        Toast.makeText(
                            this,
                            "✅ Firestore OK (${result.size()} docs)",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.wtf(TAG, "❌ ERROR FIRESTORE: ${e.message}")
                        Toast.makeText(
                            this,
                            "❌ Error: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            } else {
                Log.wtf(TAG, "❌ Firestore es NULL")
                Toast.makeText(this, "❌ Firestore NULL", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Log.wtf(TAG, "❌ EXCEPCIÓN: ${e.message}", e)
            Toast.makeText(this, "❌ Excepción: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
