package com.example.traveolas.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    fun getSharedPrefObject(context: Context): SharedPreferences{
        return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }

}