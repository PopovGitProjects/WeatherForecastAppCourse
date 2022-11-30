package com.example.weatherforecastappcourse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherforecastappcourse.OnClickItemListener
import com.example.weatherforecastappcourse.R
import com.example.weatherforecastappcourse.TabLayoutSelectTab
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.ForecastItemBinding
import com.example.weatherforecastappcourse.domain.Concat
import com.example.weatherforecastappcourse.domain.ConvertWeatherParam
import com.example.weatherforecastappcourse.domain.SharedPreference
import com.example.weatherforecastappcourse.models.WeatherModel

class RecyclerViewAdapter(
        private val itemListener: OnClickItemListener?,
        private val tabLayoutListener: TabLayoutSelectTab?,
        typeWeatherData: String,
        context: Context
    ) : ListAdapter<WeatherModel, RecyclerViewAdapter.Holder>(Comparator()) {
    private val aTypeWeatherData = typeWeatherData
    private val aContext = context

    class Holder(
        private val binding: ForecastItemBinding,
        aTypeWeatherData: String,
        private val onClickItemListener: OnClickItemListener?,
        private val tabLayoutListener: TabLayoutSelectTab?
    ) : RecyclerView.ViewHolder(binding.root) {
        private val vType = aTypeWeatherData
        private var itemTemp: WeatherModel? = null

        init {
            itemView.setOnClickListener {
                itemTemp?.let { it1 -> onClickItemListener?.onClickItem(it1)}
                tabLayoutListener?.tabLayoutSelectTab()
            }
        }

        fun bind(item: WeatherModel, aContext: Context) = with(binding) {
            itemTemp = item
            tvItemTimeDate.text = item.time
            tvItemCondition.text = item.conditions

            val concat = Concat()
            val convert = ConvertWeatherParam()
            val pressNameSt = aContext.resources.getText(R.string.pressName).toString()
            val windDirSt = aContext.resources.getText(R.string.windDir).toString()
            val windSpeedSt = aContext.resources.getText(R.string.windSpeed).toString()
            val kmhSt = aContext.resources.getText(R.string.kmh).toString()
            val msSt = aContext.resources.getText(R.string.ms).toString()
            val hPaSt = aContext.resources.getText(R.string.hPa).toString()
            val mmHgSt = aContext.resources.getText(R.string.mmHg).toString()
            val sharedPref = SharedPreference(aContext)

            when (vType) {
                Const.DAY -> {
                    tvItemTemp.text = item.currentTemp.ifEmpty {
                        "${item.dayTemp}°C / ${item.nightTemp}°C"
                    }
                    tvItemPress.text = item.pressure
                    tvItemWindDir.visibility  = View.GONE
                    tvItemWindSpeed.visibility = View.GONE
                    imgItemWeather.load("https:" + item.imageUrl)
                }
                Const.HOUR -> {
                    val currentTemp = "${item.currentTemp}°C"
                    tvItemTemp.text = currentTemp
                    if (sharedPref.getSet().pressure == "mm"){
                        val press = concat.concatenate(
                            pressNameSt,
                            convert.convertPress(item.pressure),
                            mmHgSt
                        )
                        tvItemPress.text = press
                    }else{
                        tvItemPress.text = concat.concatenate(pressNameSt, item.pressure, hPaSt)
                    }
                    tvItemWindDir.text = concat.concatenate(windDirSt, item.wind_dir, "")
                    if (sharedPref.getSet().wind == "mm"){
                        val wind = concat.concatenate(
                            windSpeedSt,
                            convert.convertPress(item.wind_kph),
                            msSt
                        )
                        tvItemWindSpeed.text = wind
                    }else{
                        tvItemWindSpeed.text = concat.concatenate(windSpeedSt, item.wind_kph, kmhSt)
                    }
                    imgItemWeather.load("https:" + item.imageUrl)
                }
                else -> {}
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            aTypeWeatherData,
            itemListener,
            tabLayoutListener
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), aContext)
    }
}