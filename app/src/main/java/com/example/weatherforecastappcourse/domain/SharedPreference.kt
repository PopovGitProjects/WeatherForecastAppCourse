package com.example.weatherforecastappcourse.domain

import android.content.Context
import com.example.weatherforecastappcourse.models.Settings
import com.example.weatherforecastappcourse.models.SettingsParam

private const val SHARED_PREF_NAME = "shared_pref_name"
private const val KEY_PRESSURE = "pressure"
private const val PRESS_PARAM = "pressure_param"
private const val KEY_WIND = "wind"
private const val WIND_PARAM = "wind_param"

class SharedPreference(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getSet(): Settings {
        val pressure = sharedPreferences.getInt(KEY_PRESSURE, -1)
        val wind = sharedPreferences.getInt(KEY_WIND, -1)
        return Settings(pressure = pressure, wind = wind)
    }

    fun getSetParam(): BooleanArray {
        val press = sharedPreferences.getBoolean(PRESS_PARAM, false)
        val wind = sharedPreferences.getBoolean(WIND_PARAM, false)
        return booleanArrayOf(press, wind)
    }

    fun saveSet(param: Settings): Boolean {
        param.pressure?.let { sharedPreferences.edit().putInt(KEY_PRESSURE, it).apply() }
        param.wind?.let { sharedPreferences.edit().putInt(KEY_WIND, it).apply() }
        return true
    }

    fun saveSetParam(param: SettingsParam): Boolean {
        param.pressParam?.let { sharedPreferences.edit().putBoolean(PRESS_PARAM, it).apply() }
        param.windParam?.let { sharedPreferences.edit().putBoolean(WIND_PARAM, it).apply() }
        return true
    }
}