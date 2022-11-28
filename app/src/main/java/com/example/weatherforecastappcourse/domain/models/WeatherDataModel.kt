package com.example.weatherforecastappcourse.domain.models

data class WeatherDataModel(
    val city: String,
    val time: String,
    val conditions: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hoursForecast: String
)
