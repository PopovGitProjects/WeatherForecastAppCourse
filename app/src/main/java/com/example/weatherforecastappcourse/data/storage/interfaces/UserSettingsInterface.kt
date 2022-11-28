package com.example.weatherforecastappcourse.data.storage.interfaces

import com.example.weatherforecastappcourse.data.storage.models.Settings

interface UserSettingsInterface {
    fun getSet(): Settings
    fun saveSet(param: Settings): Boolean
}