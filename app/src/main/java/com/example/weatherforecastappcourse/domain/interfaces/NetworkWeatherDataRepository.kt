package com.example.weatherforecastappcourse.domain.interfaces

import com.example.weatherforecastappcourse.domain.models.WeatherDataModel

interface NetworkWeatherDataRepository {
    fun getWeatherData(): WeatherDataModel
}