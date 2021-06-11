package com.dhxxn.untilwhenaos

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
            context.getSharedPreferences("account", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

//    fun clearUser(context: Context){
//        val pref : SharedPreferences = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
//        val editor : SharedPreferences.Editor = pref.edit()
//        editor.clear()
//        editor.commit()
//    }
}