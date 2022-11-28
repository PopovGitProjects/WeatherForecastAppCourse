package com.example.weatherforecastappcourse.network.interfaces

import com.example.weatherforecastappcourse.network.models.NetworkDataModel

interface NetworkWeatherDataInterface {
    fun getData(): NetworkDataModel
}