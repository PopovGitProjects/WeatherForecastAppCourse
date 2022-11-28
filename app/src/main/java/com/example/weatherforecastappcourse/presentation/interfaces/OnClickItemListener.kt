package com.example.weatherforecastappcourse.presentation.interfaces

import com.example.weatherforecastappcourse.domain.models.WeatherDataModel

interface OnClickItemListener {
    fun onClickItem(item: WeatherDataModel)
}