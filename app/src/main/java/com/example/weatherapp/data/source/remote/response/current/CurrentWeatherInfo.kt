package com.example.weatherapp.data.source.remote.response.current

import com.example.weatherapp.data.source.remote.response.dataclass.Rain
import com.example.weatherapp.data.source.remote.response.dataclass.Weather
import com.google.gson.annotations.SerializedName

data class CurrentWeatherInfo(
    val dt: Long,
   // val sunrise: Long,
    //val sunset: Long,
    val temp: Float,
   // @SerializedName("feels_like")
    //val humanTemp: Float,
    val pressure: Int,
    val humidity: Int,
    //@SerializedName("dew_point")
    //val dewPoint: Float,
    //val uvi: Float,
    val clouds: Int,
    //val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    //@SerializedName("wind_deg")
    //val windDegree: Int,
    @SerializedName("weather")
    val weatherList: List<Weather>,
    val rain: Rain
)
