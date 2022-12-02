package com.example.weatherforecastappcourse.domain

import com.example.weatherforecastappcourse.constants.Const

class ConvertWeatherParam {
    fun convertWind(param: String): String{
        val wind = param.toDouble()
        return CalcPress().decimalRound(wind / 3.6).toString() // если будет много нулей сделать приведение по CalcPress
    }
    fun convertPress(param: String): String{
        val press = param.toDouble()
        return CalcPress().decimalRound(press * Const.PA_CONST / 10.0).toString() // если будет много нулей сделать приведение по CalcPress
    }

}