package com.example.weatherapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.databinding.DailyWeatherItemBinding
import com.example.weatherapp.getCDegreeFormat
import com.example.weatherapp.getDisplayedDate
import com.example.weatherapp.getWeekDay
import java.util.*

class DailyWeatherAdapter(
    private var context: Context,
) : ListAdapter<DailyWeatherInfo,DailyWeatherAdapter.CustomViewHolder>(DailyWeatherInfoDiffUtil()) {

    class CustomViewHolder(var binding: DailyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val dailyWeatherItemBinding: DailyWeatherItemBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context),R.layout.daily_weather_item,parent,false)
        return CustomViewHolder(dailyWeatherItemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.dailyWeatherInfo = getItem(position)
        displayDate(holder,position)
        // Display the weather degree in celsius format
        holder.binding.tvDailyWeatherItemDegree.text =
            getCDegreeFormat(Math.round((getItem(position).temp.day - 273.15)).toFloat())
        loadWeatherIcon(holder)
    }

    // Displays everything related to the date .
    // Day of the week, day of the month, month and current year
    private fun displayDate(holder: CustomViewHolder,position: Int){
        val dt = holder.binding.dailyWeatherInfo?.dt?.times(1000)
        holder.binding.tvDailyWeatherItemDate.text = getDisplayedDate(dt ?: -1)
        if(position == 0){
            holder.binding.tvDailyWeatherItemDay.text = "Today"
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
}


private class DailyWeatherInfoDiffUtil : DiffUtil.ItemCallback<DailyWeatherInfo>(){
    override fun areItemsTheSame(oldItem: DailyWeatherInfo, newItem: DailyWeatherInfo): Boolean {
       return  oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DailyWeatherInfo, newItem: DailyWeatherInfo): Boolean {
        return oldItem == newItem
    }

}