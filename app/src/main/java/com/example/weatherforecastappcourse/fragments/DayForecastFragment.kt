package com.example.weatherforecastappcourse.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherforecastappcourse.R
import com.example.weatherforecastappcourse.models.viewmodels.DayForecastViewModel

class DayForecastFragment : Fragment() {

    companion object {
        fun newInstance() = DayForecastFragment()
    }

    private lateinit var viewModel: DayForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}