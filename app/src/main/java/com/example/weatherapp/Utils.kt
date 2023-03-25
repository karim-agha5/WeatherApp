package com.example.weatherapp

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

private  val days = arrayOf(
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
)

//private val calendar: Calendar = Calendar.getInstance()

fun getWeekDay(dt: Long) : String{
    if(dt < 0) return "N/A"
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = Date(dt)
    Log.i("Exception", "$dt | ${calendar.time}")
    return days[(calendar.get(Calendar.DAY_OF_WEEK)) - 1]
}

fun getCDegreeFormat(degree: Float) : String{
    return degree.toInt().toString() + "\u00B0"
}

@BindingAdapter("loadApiWeatherIcon")
fun loadApiWeatherIcon(
    imageView: ImageView,
    imageUrl: String
){
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}