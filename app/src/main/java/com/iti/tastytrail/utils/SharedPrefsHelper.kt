package com.iti.tastytrail.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsHelper {
    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(context: Context): Boolean =
        getPrefs(context).getBoolean(Constants.IS_LOGGED_IN, false)

    fun setLoggedIn(context: Context, value: Boolean) {
        getPrefs(context).edit().putBoolean(Constants.IS_LOGGED_IN, value).apply()
    }
}
