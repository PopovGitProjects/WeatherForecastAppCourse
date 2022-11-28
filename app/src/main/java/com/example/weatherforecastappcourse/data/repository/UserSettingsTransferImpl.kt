package com.example.weatherforecastappcourse.data.repository

import com.example.weatherforecastappcourse.data.storage.interfaces.UserSettingsInterface
import com.example.weatherforecastappcourse.data.storage.models.Settings
import com.example.weatherforecastappcourse.domain.interfaces.UserSettingsRepository
import com.example.weatherforecastappcourse.domain.models.UserSettings

class UserSettingsTransferImpl(private val userSettings: UserSettingsInterface): UserSettingsRepository {
    override fun saveSettings(params: UserSettings): Boolean {
        return userSettings.saveSet(mapToStorage(param = params))
    }

    override fun getSettings(): UserSettings {
        return mapToDomain(userSettings.getSet())
    }

    private fun mapToDomain(params: Settings): UserSettings {
        return UserSettings(pressSet = params.pressure, visSet = params.vis)
    }

    private fun mapToStorage(param: UserSettings): Settings {
        return Settings(pressure = param.pressSet, vis = param.visSet)
    }

}