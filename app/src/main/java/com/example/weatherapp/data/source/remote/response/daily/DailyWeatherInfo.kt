package com.example.weatherapp.data.source.remote.response.daily

import com.example.weatherapp.data.source.remote.response.dataclass.HumanTemperature
import com.example.weatherapp.data.source.remote.response.dataclass.Temperature
import com.example.weatherapp.data.source.remote.response.dataclass.Weather
import com.google.gson.annotations.SerializedName

data class DailyWeatherInfo(
    val dt: Long,
    //val sunrise: Long,
    //val sunset: Long,
    //val moonrise: Long,
    //val moonset: Long,
    val temp: Temperature,
    //@SerializedName("feels_like")
    //val humanTemperature: HumanTemperature,
    val pressure: Int,
    val humidity: Int,
    //@SerializedName("dew_point")
    //val dewPoint: Float,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    //@SerializedName("wind_deg")
    //val windDegree: Int,
    @SerializedName("weather")
    val weatherList: List<Weather>,
    val clouds: Int,
    //val pop: Float,
    val rain: Float,
    //val uvi: Float
)
