package com.example.weatherapp.data.source.remote.response

import com.example.weatherapp.data.source.remote.response.dataclass.*
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    var coord: Coord,
    @SerializedName("weather")
    var weatherList: List<Weather>,
    var main: Main,
    var visibility: String,
    var wind: Wind,
    var rain: Rain,
    var clouds: Clouds,
    var dt: Long,
    @SerializedName("sys")
    var city: City,
    var timezone: Int,
    var id: Int,
    var name: String,
    var code: Int
)