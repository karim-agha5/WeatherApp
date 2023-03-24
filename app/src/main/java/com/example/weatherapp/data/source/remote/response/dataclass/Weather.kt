package com.example.weatherapp.data.source.remote.response.dataclass

data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)
