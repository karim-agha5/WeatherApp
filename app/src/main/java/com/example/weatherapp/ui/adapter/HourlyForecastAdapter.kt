package com.example.weatherapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.*
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.example.weatherapp.databinding.HourlyForecastItemBinding
import com.example.weatherapp.util.*

class HourlyForecastAdapter(val settingsManager: SettingsManager)
    : ListAdapter<HourlyWeatherInfo,HourlyForecastAdapter.CustomViewHolder>(HourlyForecastDiffUtil()) {


    private var weatherTemp: Double = 0.0

    class CustomViewHolder(val hourlyForecastItemBinding: HourlyForecastItemBinding) :
        RecyclerView.ViewHolder(hourlyForecastItemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val hourlyForecastItemBinding: HourlyForecastItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.hourly_forecast_item,
                parent,
                false
            )
        return CustomViewHolder(hourlyForecastItemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.hourlyForecastItemBinding.imageUrl =
            getWeatherIconUrl(getItem(position).weatherList[0].icon)
       // val degree = (((getItem(position).temp - 273.15) * 10).toInt()) / 10.0
        val degree = getItem(position).temp.toDouble()
        convertData(degree)
        holder.hourlyForecastItemBinding.tvHourlyWeatherDegree.text =
            getDegreeFormat(weatherTemp)
        holder.hourlyForecastItemBinding.tvHourlyWeatherStatus.text =
            getItem(position).weatherList[0].main
        holder.hourlyForecastItemBinding.tvHourlyWeatherDate.text =
            getDisplayedTime(getItem(position).dt * fromSecondsToMillisConversionUnits)
    }

    private fun convertData(temp: Double) {
        weatherTemp = when {
            settingsManager
                .isUserSettingsTemperatureSetToCelsius() -> {
                Converter.kelvinToCelsius(temp)
            }

            settingsManager
                .isUserSettingsTemperatureSetToFahrenheit() -> {
                Converter.kelvinToFahrenheit(temp)
            }

            else -> temp
        }
    }

    private fun getDegreeFormat(temp: Double): String {
        return when {
            settingsManager
                .isUserSettingsTemperatureSetToCelsius() ->
                getTemperatureUnit(temp.toInt(), CELSIUS)

            settingsManager
                .isUserSettingsTemperatureSetToFahrenheit() ->
                getTemperatureUnit(temp.toInt(), FAHRENHEIT)

            else -> getTemperatureUnit(temp.toInt(), KELVIN)
        }

    }
}

private class HourlyForecastDiffUtil : DiffUtil.ItemCallback<HourlyWeatherInfo>(){
    override fun areItemsTheSame(oldItem: HourlyWeatherInfo, newItem: HourlyWeatherInfo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: HourlyWeatherInfo,
        newItem: HourlyWeatherInfo
    ): Boolean {
        return oldItem == newItem
    }

}