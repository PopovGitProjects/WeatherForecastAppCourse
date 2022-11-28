package com.example.weatherforecastappcourse.domain.interfaces

import com.example.weatherforecastappcourse.domain.models.UserSettings

interface UserSettingsRepository {
    fun saveSettings(params: UserSettings): Boolean
    fun getSettings(): UserSettings
}