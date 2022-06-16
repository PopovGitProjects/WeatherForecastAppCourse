package com.example.weatherforecastappcourse

data class DayItem(
    val city: String,
    val time: String,
    val condition:String,
    val imageURL: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val hoursForecast: String
)
