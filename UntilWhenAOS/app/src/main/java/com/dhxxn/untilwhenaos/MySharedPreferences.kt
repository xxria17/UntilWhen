package com.dhxxn.untilwhenaos

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val ACCOUNT : String = "account"

    fun setUserToken(context: Context, token: String) {
        val pref : SharedPreferences = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putString("TOKEN", token)
        editor.commit()
    }

    fun getUserToken(context: Context) : String {
        val pref : SharedPreferences = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        return pref.getString("TOKEN", "").toString()
    }

    fun clearUser(context: Context){
        val pref : SharedPreferences = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}