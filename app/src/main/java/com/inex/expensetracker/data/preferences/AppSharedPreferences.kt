package com.inex.expensetracker.data.preferences

import android.content.SharedPreferences

class AppSharedPreferences(private val sharedPref: SharedPreferences) {

    companion object {
        const val PREF_FILE_KEY = "com.inex.expensetracker.db_preferences"
        const val PREF_DB_SAVED_PRE_POPULATED = "db_pre_populated"
    }

    fun save(KEY_NAME: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, value)
        editor.commit()
    }

    fun getBooleanValue(KEY_NAME: String): Boolean {
        return sharedPref.getBoolean(KEY_NAME, false)
    }
}