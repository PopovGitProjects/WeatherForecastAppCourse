package com.example.weatherforecastappcourse.network.base

import com.example.weatherforecastappcourse.domain.interfaces.NetworkWeatherDataRepository
import com.example.weatherforecastappcourse.domain.models.WeatherDataModel
import com.example.weatherforecastappcourse.network.interfaces.NetworkWeatherDataInterface
import com.example.weatherforecastappcourse.network.models.NetworkDataModel

class NetworkWeatherDataImplementation(private val dataInterface: NetworkWeatherDataInterface): NetworkWeatherDataRepository {
    override fun getWeatherData(): WeatherDataModel {
        return mapToDomain(dataInterface.getData())

    }
    private fun mapToDomain(networkDataModel: NetworkDataModel): WeatherDataModel{
        return WeatherDataModel(
            city = networkDataModel.location.name,
            time = networkDataModel.current.last_updated,
            conditions = networkDataModel.current.condition.text,
            currentTemp = networkDataModel.current.temp_c.toString(),
            maxTemp = networkDataModel.forecast.forecastday[0].day.maxtemp_c.toString(),
            minTemp = networkDataModel.forecast.forecastday[0].day.mintemp_c.toString(),
            imageUrl = networkDataModel.current.condition.icon,
            hoursForecast = ""

        )
    }
}