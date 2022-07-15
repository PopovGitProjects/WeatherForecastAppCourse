package com.example.weatherforecastappcourse.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.ForecastItemBinding
import com.example.weatherforecastappcourse.models.WeatherModel

class RecyclerViewAdapter(private val dataSet: ArrayList<WeatherModel>, typeWeatherData: String): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val aDataSet = arrayListOf<WeatherModel>()
    private val aTypeWeatherData = typeWeatherData

    class ViewHolder(private val binding: ForecastItemBinding, aTypeWeatherData: String): RecyclerView.ViewHolder(binding.root) {
        private val vType = aTypeWeatherData
        fun init(item: WeatherModel) = with(binding){
            tvItemTimeDate.text = item.time
            tvItemCondition.text = item.conditions
            when (vType){
                Const.CURRENT -> {
                    tvItemTemp.text = item.dayTemp
                    imgItemWeather.setImageURI(Uri.parse(item.imageUrl))
                }
                Const.HOUR -> {
                    tvItemTemp.text = item.hoursForecast
                    imgItemWeather.setImageURI(Uri.parse(item.imageUrl))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView = aDataSet[position]
    }

    override fun getItemCount() = aDataSet.size


}