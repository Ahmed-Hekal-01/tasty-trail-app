package com.iti.tastytrail.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "recipe_prefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_FIRST_TIME = "first_time"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_LANGUAGE = "language"
    }

    // Login status
    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_IS_LOGGED_IN, value) }

    // User ID
    var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, null)
        set(value) = sharedPreferences.edit { putString(KEY_USER_ID, value) }

    // User email
    var userEmail: String?
        get() = sharedPreferences.getString(KEY_USER_EMAIL, null)
        set(value) = sharedPreferences.edit { putString(KEY_USER_EMAIL, value) }

    // User name
    var userName: String?
        get() = sharedPreferences.getString(KEY_USER_NAME, null)
        set(value) = sharedPreferences.edit {putString(KEY_USER_NAME, value) }

    // Auth token
    var authToken: String?
        get() = sharedPreferences.getString(KEY_AUTH_TOKEN, null)
        set(value) = sharedPreferences.edit {putString(KEY_AUTH_TOKEN, value) }

    // First time user
    var isFirstTime: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_TIME, true)
        set(value) = sharedPreferences.edit {putBoolean(KEY_FIRST_TIME, value) }

    // Theme mode
    var themeMode: String?
        get() = sharedPreferences.getString(KEY_THEME_MODE, "system")
        set(value) = sharedPreferences.edit {putString(KEY_THEME_MODE, value) }

    // Language
    var language: String?
        get() = sharedPreferences.getString(KEY_LANGUAGE, "en")
        set(value) = sharedPreferences.edit {putString(KEY_LANGUAGE, value)}

    fun saveUserData(
        userId: String,
        email: String,
        name: String,
        token: String? = null
    ) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            token?.let { putString(KEY_AUTH_TOKEN, it) }
            apply()
        }
    }

    // Clear all user data (logout)
    fun clearUserData() {
        sharedPreferences.edit().apply {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_USER_ID)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_NAME)
            remove(KEY_AUTH_TOKEN)
            apply()
        }
    }

    // Clear specific data
    fun clearAuthToken() {
        sharedPreferences.edit { remove(KEY_AUTH_TOKEN) }
    }

    // Clear all preferences (complete reset)
    fun clearAll() {
        sharedPreferences.edit { clear() }
    }
}
