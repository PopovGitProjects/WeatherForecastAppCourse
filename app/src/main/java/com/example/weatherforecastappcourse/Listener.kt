package com.example.weatherforecastappcourse

import com.example.weatherforecastappcourse.models.WeatherModel

interface Listener {
    fun onClick(item: WeatherModel)
}