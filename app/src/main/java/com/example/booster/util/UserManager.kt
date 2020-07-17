package com.example.booster.util

import android.content.Context
import android.content.SharedPreferences

object UserManager {
    private lateinit var pref: SharedPreferences
    fun init(context: Context) {
        pref = context.getSharedPreferences("Booster", Context.MODE_PRIVATE)
    }

    var token: String?
        get() = pref.getString("token", null)
        set(value) = pref.edit {
            it.putString("token", value)
        }

    var univ: Int?
    get() = pref.getInt("univ", 1)
    set(value) = pref.edit{
        if (value != null) {
            it.putInt("univ", value)
        }
    }

    fun logOut(){
        token = null
    }

    private inline fun SharedPreferences.edit(
        operation:
            (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }
}