package com.example.weatherforecastappcourse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastappcourse.adapters.RecyclerViewAdapter
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentHourForecastBinding
import com.example.weatherforecastappcourse.models.WeatherModel

class HourForecastFragment : Fragment() {

    private var _binding: FragmentHourForecastBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerViewAdapter? = null

    private val dataSet = arrayListOf(
        WeatherModel("", "12:00", "Sunny", "", "", "", "", "+22"),
        WeatherModel("", "13:00", "Sunny", "", "", "", "", "+23"),
        WeatherModel("", "14:00", "Sunny", "", "", "", "", "+25")
    )

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
    }

    private fun initRecyclerView() = with(binding){
        recyclerViewHour.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter(Const.HOUR)
        recyclerViewHour.adapter = adapter
        adapter!!.submitList(dataSet)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}