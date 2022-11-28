package com.example.weatherforecastappcourse.data.storage.sharedpref

import android.content.Context
import com.example.weatherforecastappcourse.data.storage.interfaces.UserSettingsInterface
import com.example.weatherforecastappcourse.data.storage.models.Settings

private const val SHARED_PREF_NAME = "shared_pref_name"
private const val KEY_PRESSURE = "pressure"
private const val KEY_VIS = "vis"
private const val DEFAULT_PRESSURE = "default_pressure"
private const val DEFAULT_VIS = "default_vis"

class SharedPrefUserStorage(context: Context): UserSettingsInterface {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun getSet(): Settings {
        val pressure = sharedPreferences.getString(KEY_PRESSURE, "")?: DEFAULT_PRESSURE
        val vis = sharedPreferences.getString(KEY_VIS, "")?: DEFAULT_VIS
        return Settings(pressure = pressure, vis = vis)
    }

    override fun saveSet(param: Settings): Boolean {
        sharedPreferences.edit().putString(KEY_PRESSURE, param.pressure).apply()
        sharedPreferences.edit().putString(KEY_VIS, param.vis).apply()
        return true
    }
}