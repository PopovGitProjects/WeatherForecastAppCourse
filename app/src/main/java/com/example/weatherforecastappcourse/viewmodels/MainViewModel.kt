package com.example.weatherforecastappcourse.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastappcourse.domain.models.WeatherDataModel

class MainViewModel: ViewModel() {
    val liveCurrentData = MutableLiveData<WeatherDataModel>()
    val livesDayList = MutableLiveData<List<WeatherDataModel>>()

//    fun requestWeatherData(city: String, context: Context) {
//        val url = Const.API_URL +
//                Const.API_KEY +
//                "&q=" +
//                city +
//                "&days=" +
//                "5" +
//                "&aqi=no" +
//                "&alerts=no"
//        val queue = Volley.newRequestQueue(context)
//        val request = StringRequest(
//            Request.Method.GET,
//            url,
//            {
//                    result -> parseWeatherData(result)
//            },
//            {
//                    error ->
//                run {
//                    Log.e("My", "Volley ERROR: $error")
//                    Toast.makeText(context, "Internet is not connected!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        )
//        queue.add(request)
//    }
//    private fun parseWeatherData(result: String) {
//        val mainObject = JSONObject(result)
//        val list = parseDaysWeatherData(mainObject)
//        livesDayList.value = list
//        parseCurrentWeatherData(mainObject, list[0])
//    }
//    private fun parseDaysWeatherData(mainObject: JSONObject): List<WeatherModel>{
//        val list = ArrayList<WeatherModel>()
//        val name = mainObject.getJSONObject("location").getString("name")
//        val daysForecastArray = mainObject.getJSONObject("forecast")
//            .getJSONArray("forecastday")
//        for (i in 0 until daysForecastArray.length()){
//            val day = daysForecastArray[i] as JSONObject
//            val item = WeatherModel(
//                name,
//                day.getString("date"),
//                day.getJSONObject("day").getJSONObject("condition")
//                    .getString("text"),
//                "",
//                day.getJSONObject("day").getString("maxtemp_c"),
//                day.getJSONObject("day").getString("mintemp_c"),
//                day.getJSONObject("day").getJSONObject("condition")
//                    .getString("icon"),
//                day.getJSONArray("hour").toString()
//            )
//            list.add(item)
//        }
//        return list
//    }
//    private fun parseCurrentWeatherData(mainObject: JSONObject, weatherItem: WeatherModel){
//        val item = WeatherModel(
//            mainObject.getJSONObject("location").getString("name"),
//            mainObject.getJSONObject("current").getString("last_updated"),
//            mainObject.getJSONObject("current").getJSONObject("condition")
//                .getString("text"),
//            mainObject.getJSONObject("current").getString("temp_c"),
//            weatherItem.dayTemp,
//            weatherItem.nightTemp,
//            mainObject.getJSONObject("current").getJSONObject("condition")
//                .getString("icon"),
//            weatherItem.hoursForecast
//        )
//        liveCurrentData.value = item
//        Log.d("My", "item: $item")
//    }
}