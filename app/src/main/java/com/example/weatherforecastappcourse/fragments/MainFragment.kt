package com.example.weatherforecastappcourse.fragments

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import coil.load
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherforecastappcourse.adapters.ViewPagerAdapter
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentMainBinding
import com.example.weatherforecastappcourse.models.WeatherModel
import com.example.weatherforecastappcourse.models.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject

class MainFragment : Fragment() {
    private val fragmentList = listOf(
        HourForecastFragment.newInstance(),
        DayForecastFragment.newInstance()
    )
    private val fragmentTitleList = listOf(
        "Hour",
        "Day"
    )
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var _pLauncher: ActivityResultLauncher<String>? = null
    private val pLauncher get() = _pLauncher!!
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        requestWeatherData("Perm", requireContext())
        updateCurrentCard()
    }

    private fun init() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2){
                tab, pos -> tab.text = fragmentTitleList[pos]

        }.attach()
    }

    private fun updateCurrentCard() = with(binding){
        model.liveCurrentData.observe(viewLifecycleOwner){
            val maxMinTemp = "${it.dayTemp}\u00B0C / ${it.nightTemp}\u00B0C"
            val currentTemp = "${it.currentTemp}\u00B0C"
            tvData.text = it.time
            imgWeather.load("https:" + it.imageUrl)
            tvCity.text = it.city
            tvCurrentTemp.text = currentTemp
            tvCondition.text = it.conditions
            tvMaxMin.text = maxMinTemp
        }
        Log.d("My", "${model.liveCurrentData.value}")
    }

    private fun permissionListener(){
        _pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkPermission(){
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeatherData(city: String, context: Context){
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
                    result -> parseWeatherData(result)
            },
            {
                    error ->
                run {
                    Log.e("My", "Volley ERROR: $error")
                    Toast.makeText(context, "Internet is not connected!", Toast.LENGTH_SHORT).show()
                }
            }
        )
        queue.add(request)
    }

    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDaysWeatherData(mainObject)
        parseCurrentWeatherData(mainObject, list[0])
    }

    private fun parseDaysWeatherData(mainObject: JSONObject): List<WeatherModel>{
        val list = ArrayList<WeatherModel>()
        val name = mainObject.getJSONObject("location").getString("name")
        val daysForecastArray = mainObject.getJSONObject("forecast")
            .getJSONArray("forecastday")
        for (i in 0 until daysForecastArray.length()){
            val day = daysForecastArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        model.livesDayList.value = list
        return list
    }

    private fun parseCurrentWeatherData(mainObject: JSONObject, weatherItem: WeatherModel){
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition")
                .getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.dayTemp,
            weatherItem.nightTemp,
            mainObject.getJSONObject("current").getJSONObject("condition")
                .getString("icon"),
            weatherItem.hoursForecast
        )
        model.liveCurrentData.value = item
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _pLauncher = null
    }
}