package com.example.weatherforecastappcourse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastappcourse.adapters.RecyclerViewAdapter
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentHourForecastBinding
import com.example.weatherforecastappcourse.models.WeatherModel
import com.example.weatherforecastappcourse.models.viewmodels.MainViewModel
import org.json.JSONArray
import org.json.JSONObject

class HourForecastFragment : Fragment() {

    private var _binding: FragmentHourForecastBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerViewAdapter? = null
    private val model: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = HourForecastFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHourForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        model.liveCurrentData.observe(viewLifecycleOwner){
            adapter?.submitList(getHoursList(it))

        }
    }

    private fun initRecyclerView() = with(binding){
        recyclerViewHour.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter(Const.HOUR)
        recyclerViewHour.adapter = adapter
    }

    private fun getHoursList(weatherItem: WeatherModel): List<WeatherModel>{
        val hoursArray = JSONArray(weatherItem.hoursForecast)
        val list = ArrayList<WeatherModel>()
        for (i in 0 until hoursArray.length()){
            val item = WeatherModel(
                weatherItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("text"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "",
                "",
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("icon"),
                ""
            )
            list.add(item)
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}