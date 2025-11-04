package com.example.miniproyecto1.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("logged_in", false)
    }

    fun setLoggedIn(value: Boolean) {
        prefs.edit().putBoolean("logged_in", value).apply()
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
