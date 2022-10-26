package com.example.weatherforecastappcourse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastappcourse.adapters.RecyclerViewAdapter
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentDayForecastBinding
import com.example.weatherforecastappcourse.models.WeatherModel

class DayForecastFragment : Fragment() {

    private var _binding: FragmentDayForecastBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerViewAdapter? = null

    private val dataSet = arrayListOf(
        WeatherModel("", "25.07", "Sunny", "", "+25", "", "", ""),
        WeatherModel("", "26.07", "Sunny", "", "+27", "", "", ""),
        WeatherModel("", "27.07", "Sunny", "", "+32", "", "", "")
    )

    companion object {
        fun newInstance() = DayForecastFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding){
        recyclerViewDay.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter(Const.DAY)
        recyclerViewDay.adapter = adapter
        adapter!!.submitList(dataSet)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}