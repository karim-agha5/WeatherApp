package com.example.weatherapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

const val KELVIN = 0
const val CELSIUS = 1
const val FAHRENHEIT = 2
const val fromSecondsToMillisConversionUnits = 1000
const val TAG = "Exception"

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

fun getDisplayedTime(timeInMillis: Long) : String{
    if(timeInMillis < 0) return "N/A"
    calendar.time = Date(timeInMillis)
    val displayedTime: StringBuilder = StringBuilder()
    displayedTime.append(calendar.get(Calendar.HOUR) + 1)
    val exactTimingOfTheDay = if(calendar.get(Calendar.HOUR_OF_DAY) < 12) "AM" else "PM"
    displayedTime.append(" $exactTimingOfTheDay")
    return displayedTime.toString()
}

fun getCDegreeFormat(degree: Float) : String{
    return degree.toInt().toString() + "\u00B0"
}

fun getHumidityUnit(humidity: Int) : String{
   //return "$humidity g.m\u2073"
    return "$humidity%"
}

fun getPressureUnit(pressure: Int) : String{
    return "$pressure hPa"
}

fun getCloudsUnit(clouds: Int) : String{
    return "$clouds%"
}

fun getTemperatureUnit(temperature: Float,temperatureUnit: Int) : String{
    return when (temperatureUnit) {
        KELVIN -> "$temperature K"
        CELSIUS -> "$temperature \u2103"
        else -> "$temperature \u2109"
    }
}

fun getWindSpeedUnit(windSpeed: Float,isUnitInMeterPerSec: Boolean) : String{
    return if(isUnitInMeterPerSec) "$windSpeed meter/sec"
    else "$windSpeed miles/hour"
}

fun getWeatherIconUrl(iconId: String) = "https://openweathermap.org/img/wn/$iconId@2x.png"

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
