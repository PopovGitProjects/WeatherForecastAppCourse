package com.example.weatherforecastappcourse.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HourForecastViewModel : ViewModel() {
    val liveData = MutableLiveData<String>()
    val liveDataList = MutableLiveData<List<String>>()
}