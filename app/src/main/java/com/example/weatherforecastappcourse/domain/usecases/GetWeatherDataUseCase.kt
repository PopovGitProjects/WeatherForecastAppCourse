package com.example.weatherforecastappcourse.domain.usecases

import com.example.weatherforecastappcourse.domain.interfaces.NetworkWeatherDataRepository
import com.example.weatherforecastappcourse.domain.models.WeatherDataModel

class GetWeatherDataUseCase(private val networkWeatherData: NetworkWeatherDataRepository) {
    fun execute(): WeatherDataModel{
        return WeatherDataModel(
            city = networkWeatherData.getWeatherData().city,
            time = networkWeatherData.getWeatherData().time,
            conditions = networkWeatherData.getWeatherData().conditions,
            currentTemp = networkWeatherData.getWeatherData().currentTemp,
            maxTemp = networkWeatherData.getWeatherData().maxTemp,
            minTemp = networkWeatherData.getWeatherData().minTemp,
            imageUrl = networkWeatherData.getWeatherData().imageUrl,
            hoursForecast = networkWeatherData.getWeatherData().hoursForecast
        )

    }
}