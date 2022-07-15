package com.example.weatherforecastappcourse.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherforecastappcourse.R
import com.example.weatherforecastappcourse.models.viewmodels.HourForecastViewModel

class HourForecastFragment : Fragment() {

    companion object {
        fun newInstance() = HourForecastFragment()
    }

    private lateinit var viewModel: HourForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hour_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HourForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}