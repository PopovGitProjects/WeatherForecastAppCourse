package com.example.weatherforecastappcourse

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherforecastappcourse.constants.Const

class RequestWeatherData {
    fun requestWeatherData(city: String, context: Context){
        val url = Const.API_URL +
                Const.API_KEY +
                "&q=" +
                city +
                "&days=" +
                "5" +
                "&aqi=no" +
                "&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                    result -> Log.d("My", "JSON Result: $result")
            },
            {
                    error -> Log.e("My", "Volley ERROR: $error")
            }
        )
        queue.add(request)
    }
}