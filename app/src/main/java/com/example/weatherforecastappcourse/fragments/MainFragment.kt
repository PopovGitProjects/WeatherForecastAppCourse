package com.example.weatherforecastappcourse.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import coil.load
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherforecastappcourse.DialogManager
import com.example.weatherforecastappcourse.OnClickDialogButtonListener
import com.example.weatherforecastappcourse.R
import com.example.weatherforecastappcourse.TabLayoutSelectTab
import com.example.weatherforecastappcourse.adapters.ViewPagerAdapter
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentMainBinding
import com.example.weatherforecastappcourse.domain.ConvertWeatherParam
import com.example.weatherforecastappcourse.domain.SharedPreference
import com.example.weatherforecastappcourse.models.WeatherModel
import com.example.weatherforecastappcourse.models.viewmodels.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainFragment : Fragment(), OnClickDialogButtonListener, TabLayoutSelectTab {

    private var fLocationClient: FusedLocationProviderClient? = null

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
        updateCurrentCard()
        buttonClick()
    }

    private fun init() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2){
                tab, pos -> tab.text = fragmentTitleList[pos]

        }.attach()
    }

    private fun buttonClick() = with(binding){
        btnRefresh.setOnClickListener {
            tabLayout.selectTab(tabLayout.getTabAt(0))
            checkLocation()
            refreshProgressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                refreshProgressBar.visibility = View.GONE
            }
        }
        btnSearch.setOnClickListener {
            DialogManager.searchByNameDialog(requireContext(), object : OnClickDialogButtonListener{
                override fun onClickDialogButton(name: String?) {
                    if (name != null) {
                        requestWeatherData(name, requireContext())
                    }
                }
            })
        }
    }

    private fun isLocationEnabled(): Boolean{
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun checkLocation(){
        if (isLocationEnabled()){
            getLocation()
        }else{
            DialogManager.locationSettingDialog(requireContext(), object : OnClickDialogButtonListener{
                override fun onClickDialogButton(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

            })
        }
    }

    private fun getLocation(){
        val cancellationToken = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient?.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token)
            ?.addOnCompleteListener {
                if (it.result.latitude.toString() != "" && it.result.longitude.toString() != ""){
                    requestWeatherData("${it.result.latitude}, ${it.result.longitude}"
                        , requireContext())
                }else{
                    requestWeatherData("Moscow", requireContext())
                }
            }
    }

    private fun updateCurrentCard() = with(binding){
        val text = resources.getText(R.string.app_name).toString()
        val sharedPref = SharedPreference(requireContext())
        model.liveCurrentData.observe(viewLifecycleOwner){
            val maxMinTemp = "${it.dayTemp}\u00B0C / ${it.nightTemp}\u00B0C"
            val currentTemp = if (it.currentTemp == ""){
                maxMinTemp
            }else{
                "Temperature: ${it.currentTemp}°C"
            }
            tvData.text = it.time
            imgWeather.load("https:" + it.imageUrl)
            tvCity.text = it.city
            tvCurrentTemp.text = currentTemp
            val press = if (sharedPref.getSet().pressure == "mm") {
                "Pressure: ${ConvertWeatherParam().convertPress(it.pressure)}"
            }else{
                "Pressure: ${it.pressure}"
            }
            tvPressure.text = press
            val windDir = "Wind direction: ${it.wind_dir}"
            tvWindDir.text = windDir
            val windSpeed = if (sharedPref.getSet().wind == "ms"){
                "Wind speed: ${ConvertWeatherParam().convertWind(it.wind_kph)} ms"
            }else{
                "Wind speed: ${it.wind_kph} kph $text"
            }
            tvWindSpeed.text = windSpeed
            tvCondition.text = it.conditions
            tvMaxMin.text = if (it.currentTemp.isEmpty()){
                ""
            }else{
                maxMinTemp
            }
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
                "3" +
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
                    Toast.makeText(context, "Weather data not received!", Toast.LENGTH_SHORT).show()
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
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                "",
                "",
                "",
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
            mainObject.getJSONObject("current").getString("pressure_mb"),
            mainObject.getJSONObject("current").getString("wind_dir"),
            mainObject.getJSONObject("current").getString("wind_kph"),
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

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _pLauncher = null
        fLocationClient = null
    }


    override fun onClickDialogButton(name: String?) {
    }

    override fun tabLayoutSelectTab() {
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))//???????????
    }
}