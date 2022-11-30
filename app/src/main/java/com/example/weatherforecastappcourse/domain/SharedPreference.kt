package com.example.weatherforecastappcourse.domain

import android.content.Context
import com.example.weatherforecastappcourse.models.Settings

private const val SHARED_PREF_NAME = "shared_pref_name"
private const val KEY_PRESSURE = "pressure"
private const val KEY_WIND = "wind"
private const val DEFAULT_PRESSURE = "default_pressure"
private const val DEFAULT_WIND = "default_wind"

class SharedPreference(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getSet(): Settings {
        val pressure = sharedPreferences.getString(KEY_PRESSURE, "")?: DEFAULT_PRESSURE
        val wind = sharedPreferences.getString(KEY_WIND, "")?: DEFAULT_WIND
        return Settings(pressure = pressure, wind = wind)
    }
    fun saveSet(param: Settings): Boolean {
        sharedPreferences.edit().putString(KEY_PRESSURE, param.pressure).apply()
        sharedPreferences.edit().putString(KEY_WIND, param.wind).apply()
        return true
    }
}