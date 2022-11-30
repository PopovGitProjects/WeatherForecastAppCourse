package com.example.weatherforecastappcourse.domain

import android.widget.EditText
import kotlin.math.roundToInt

class CalcPress {
    fun calculatePressure(editText: EditText, constant: Double):String{
        val pressure = editText.text.toString().toDouble()
        return decimalRound(pressure * constant).toString()
    }
    private fun decimalRound(number: Double): Double{
        var result = (number*10).roundToInt()
        val dd = number*10 - result
        if (dd >= 0.5){
            result += 1
        }
        return result/ 10.0
    }
}