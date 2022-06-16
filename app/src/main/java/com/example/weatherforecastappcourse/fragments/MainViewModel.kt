package com.example.weatherforecastappcourse.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val liveData = MutableLiveData<String>()
    val liveDataList = MutableLiveData<List<String>>()
}