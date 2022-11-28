package com.example.weatherforecastappcourse.domain.usecases

import com.example.weatherforecastappcourse.domain.interfaces.UserSettingsRepository
import com.example.weatherforecastappcourse.domain.models.UserSettings

class GetSettingsUseCase(private val userSettingsRepository: UserSettingsRepository) {
    fun execute(): UserSettings{
        return UserSettings(
            pressSet = userSettingsRepository.getSettings().pressSet,
            visSet = userSettingsRepository.getSettings().pressSet
        )
    }
}