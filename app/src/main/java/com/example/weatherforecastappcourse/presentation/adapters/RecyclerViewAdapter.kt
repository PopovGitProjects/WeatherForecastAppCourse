package com.example.weatherforecastappcourse.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherforecastappcourse.presentation.interfaces.OnClickItemListener
import com.example.weatherforecastappcourse.presentation.interfaces.TabLayoutSelectTab
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.ForecastItemBinding
import com.example.weatherforecastappcourse.domain.models.WeatherDataModel

class RecyclerViewAdapter(
    private val itemListener: OnClickItemListener?,
    private val tabLayoutListener: TabLayoutSelectTab?,
    typeWeatherData: String
    ) : ListAdapter<WeatherDataModel, RecyclerViewAdapter.Holder>(Comparator()) {
    private val aTypeWeatherData = typeWeatherData

    class Holder(
        private val binding: ForecastItemBinding,
        aTypeWeatherData: String,
        private val onClickItemListener: OnClickItemListener?,
        private val tabLayoutListener: TabLayoutSelectTab?
    ) : RecyclerView.ViewHolder(binding.root) {
        private val vType = aTypeWeatherData
        private var itemTemp: WeatherDataModel? = null

        init {
            itemView.setOnClickListener {
                itemTemp?.let { it1 -> onClickItemListener?.onClickItem(it1)}
                tabLayoutListener?.tabLayoutSelectTab()
            }
        }

        fun bind(item: WeatherDataModel) = with(binding) {
            itemTemp = item
            tvItemTimeDate.text = item.time
            tvItemCondition.text = item.conditions
            when (vType) {
                Const.DAY -> {
                    tvItemTemp.text = item.currentTemp.ifEmpty {
                        "${item.maxTemp}°C / ${item.minTemp}°C"
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

    class Comparator : DiffUtil.ItemCallback<WeatherDataModel>() {
        override fun areItemsTheSame(oldItem: WeatherDataModel, newItem: WeatherDataModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherDataModel, newItem: WeatherDataModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), aTypeWeatherData, itemListener, tabLayoutListener
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}