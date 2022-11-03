package com.example.weatherforecastappcourse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.ForecastItemBinding
import com.example.weatherforecastappcourse.models.WeatherModel

class RecyclerViewAdapter(typeWeatherData: String): ListAdapter<WeatherModel, RecyclerViewAdapter.Holder>(Comparator()){
    private val aTypeWeatherData = typeWeatherData

    class Holder(private val binding: ForecastItemBinding, aTypeWeatherData: String): RecyclerView.ViewHolder(binding.root) {
        private val vType = aTypeWeatherData
        fun bind(item: WeatherModel) = with(binding){
            tvItemTimeDate.text = item.time
            tvItemCondition.text = item.conditions
            when (vType){
                Const.DAY -> {
                    tvItemTemp.text = item.currentTemp.ifEmpty {
                        "${item.dayTemp}°C / ${item.nightTemp}°C"
                    }
                    imgItemWeather.load("https:" + item.imageUrl)
                }
                Const.HOUR -> {
                    val currentTemp = "${item.currentTemp}°C"
                    tvItemTemp.text = currentTemp
                    imgItemWeather.load("https:" + item.imageUrl)
                }
                else -> {}
            }
        }
    }
    class Comparator: DiffUtil.ItemCallback<WeatherModel>(){
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), aTypeWeatherData)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}