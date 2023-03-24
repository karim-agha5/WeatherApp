package com.example.weatherapp.data.source.remote.response.dataclass

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    var dt: Long,
    var main: Main,
    @SerializedName("weather")
    var weatherList: List<Weather>,
    var clouds: Clouds,
    var wind: Wind,
    var visibility: Int,
    var pop: Double,
    var rain: Rain,
    var sys: Sys,
    @SerializedName("dt_txt")
    var dateAsText: String
)