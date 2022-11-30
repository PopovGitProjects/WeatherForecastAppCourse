package com.example.weatherforecastappcourse.models

data class WeatherModel(
    val city: String,
    val time: String,
    val conditions: String,
    val currentTemp: String,
    val dayTemp: String,
    val nightTemp: String,
    val pressure: String,
    val wind_dir: String,
    val wind_kph: String,
    val imageUrl: String,
    val hoursForecast: String
)
