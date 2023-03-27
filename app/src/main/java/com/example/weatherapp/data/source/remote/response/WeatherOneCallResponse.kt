package com.example.weatherapp.data.source.remote.response

import com.example.weatherapp.data.source.remote.response.current.CurrentWeatherInfo
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.data.source.remote.response.dataclass.Alert
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.google.gson.annotations.SerializedName

/**
 * Wrapper for the data provided by WeatherOneCallURL
 * */

data class WeatherOneCallResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    //@SerializedName("timezone_offset")
    //val timezoneOffset: Int,
    @SerializedName("current")
    val currentWeatherDetailedInfo: CurrentWeatherInfo,
    //val minutely: List<MinutelyWeatherInfo>,
    @SerializedName("hourly")
    val twoDaysHourlyForecast: List<HourlyWeatherInfo>,
    @SerializedName("daily")
    val dailyForecast: List<DailyWeatherInfo>,
    val alerts: List<Alert>
)
