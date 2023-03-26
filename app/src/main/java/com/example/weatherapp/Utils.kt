package com.example.weatherapp

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


var months = arrayOf(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
)


private val calendar: Calendar = Calendar.getInstance()

fun getWeekDay(timeInMillis: Long) : String{
    if(timeInMillis < 0) return "N/A"
    calendar.time = Date(timeInMillis)
    return days[(calendar.get(Calendar.DAY_OF_WEEK)) - 1]
}

fun getDisplayedDate(timeInMillis: Long) : String{
    if(timeInMillis < 0) return "N/A"
    calendar.time = Date(timeInMillis)
    val displayedDate: StringBuilder = StringBuilder()
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    displayedDate.append(dayOfMonth)
    when(dayOfMonth){
        1 -> displayedDate.append("st ")
        2 -> displayedDate.append("nd ")
        3 -> displayedDate.append("rd ")
        else -> displayedDate.append("th ")
    }
    displayedDate.append(months[calendar.get(Calendar.MONTH)])
    displayedDate.append(", ")
    displayedDate.append(calendar.get(Calendar.YEAR))

    return displayedDate.toString()
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