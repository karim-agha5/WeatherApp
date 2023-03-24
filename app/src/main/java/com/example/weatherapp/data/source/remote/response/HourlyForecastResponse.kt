package com.example.weatherapp.data.source.remote.response

import com.example.weatherapp.data.source.remote.response.dataclass.City
import com.example.weatherapp.data.source.remote.response.dataclass.WeatherInfo

data class HourlyForecastResponse(
    var cod: String,
    var message: Int,
    var cnt: Int,
    var list: List<WeatherInfo>,
    var city: City
)
