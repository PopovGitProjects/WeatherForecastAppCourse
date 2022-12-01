package com.example.weatherforecastappcourse.models.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastappcourse.models.WeatherModel

class MainViewModel: ViewModel() {
    val liveCurrentData = MutableLiveData<WeatherModel>()
    val livesDayList = MutableLiveData<List<WeatherModel>>()
}