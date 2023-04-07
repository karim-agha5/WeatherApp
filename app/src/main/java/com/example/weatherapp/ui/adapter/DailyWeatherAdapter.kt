package com.example.weatherapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.*
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.databinding.DailyWeatherItemBinding
import com.example.weatherapp.util.*

class DailyWeatherAdapter(val settingsManager: SettingsManager,private val context: Context)
    : ListAdapter<DailyWeatherInfo,DailyWeatherAdapter.CustomViewHolder>(DailyWeatherInfoDiffUtil()) {

    class CustomViewHolder(var binding: DailyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)
    var maxTemp: Double = 0.0
    var minTemp: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val dailyWeatherItemBinding: DailyWeatherItemBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context),R.layout.daily_weather_item,parent,false)
        return CustomViewHolder(dailyWeatherItemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.dailyWeatherInfo = getItem(position)
        displayDate(holder,position)
        // Display the weather degree in celsius format
        maxTemp = getItem(position).temp.max
        minTemp = getItem(position).temp.min
        convertData(maxTemp,minTemp)
        val maxAndMinWeatherDegree = getDegreeFormat(maxTemp,minTemp)
        holder.binding.tvDailyWeatherItemDegree.text = maxAndMinWeatherDegree
        loadWeatherIcon(holder)
    }

    // Displays everything related to the date .
    // Day of the week, day of the month, month and current year
    private fun displayDate(holder: CustomViewHolder,position: Int){
        val dt = holder.binding.dailyWeatherInfo?.dt?.times(fromSecondsToMillisConversionUnits)
        holder.binding.tvDailyWeatherItemDate.text = getDisplayedDate(dt ?: -1)
        if(position == 0){
            holder.binding.tvDailyWeatherItemDay.text = context.getString(R.string.today)
        }
        else {
            holder.binding.tvDailyWeatherItemDay.text = getWeekDay(dt ?: -1)
        }
    }
    private fun loadWeatherIcon(holder: CustomViewHolder){
        if(holder.binding.dailyWeatherInfo != null){
            holder.binding.imageUrl = "https://openweathermap.org/img/wn/" +
                    holder.binding.dailyWeatherInfo?.weatherList?.get(0)?.icon+ "@2x.png"
        }
    }

    private fun getDegreeFormat(temp1: Double,temp2: Double) : String{
        return when{
            settingsManager
                .isUserSettingsTemperatureSetToCelsius() ->
                "${getTemperatureUnit(temp1.toInt(), CELSIUS)}/${getTemperatureUnit(temp2.toInt(), CELSIUS)}"

            settingsManager
                .isUserSettingsTemperatureSetToFahrenheit() ->
                "${getTemperatureUnit(temp1.toInt(), FAHRENHEIT)}/${getTemperatureUnit(temp2.toInt(), FAHRENHEIT)}"

            else -> "${getTemperatureUnit(temp1.toInt(), KELVIN)}/${getTemperatureUnit(temp2.toInt(), KELVIN)}"
        }
    }

    private fun convertData(temp1: Double, temp2: Double){
        when{
            settingsManager
                .isUserSettingsTemperatureSetToCelsius() -> {
                    maxTemp = Converter.kelvinToCelsius(temp1)
                    minTemp = Converter.kelvinToCelsius(temp2)
                }

            settingsManager
                .isUserSettingsTemperatureSetToFahrenheit() -> {
                    maxTemp = Converter.celsiusToFahrenheit(temp1)
                    minTemp = Converter.celsiusToFahrenheit(temp2)
                }
        }
    }
}


private class DailyWeatherInfoDiffUtil : DiffUtil.ItemCallback<DailyWeatherInfo>(){
    override fun areItemsTheSame(oldItem: DailyWeatherInfo, newItem: DailyWeatherInfo): Boolean {
       return  oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DailyWeatherInfo, newItem: DailyWeatherInfo): Boolean {
        return oldItem == newItem
    }
}