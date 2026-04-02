package com.example.coachrythmo.auth

import android.content.Context

class AuthManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TEST_CODE = "1010"
    }

    fun sendCode(phone: String): Boolean {
        return phone.isNotBlank()
    }

    fun verifyCode(code: String): Boolean {
        return code == TEST_CODE
    }

    fun login() {
        prefs.edit().putBoolean("is_logged_in", true).apply()
    }

    fun logout() {
        prefs.edit().putBoolean("is_logged_in", false).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }
}
