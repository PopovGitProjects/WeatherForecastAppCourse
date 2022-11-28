package com.example.weatherforecastappcourse.domain.usecases

import com.example.weatherforecastappcourse.domain.interfaces.UserSettingsRepository
import com.example.weatherforecastappcourse.domain.models.UserSettings

class SaveSettingsUseCase(private val userSettingsRepository: UserSettingsRepository) {
    fun execute(param: UserSettings): Boolean {
        val oldParam = userSettingsRepository.getSettings()
        if (oldParam.pressSet == param.pressSet && oldParam.visSet == param.visSet){
            return true
        }
        return userSettingsRepository.saveSettings(params = param)
    }
}