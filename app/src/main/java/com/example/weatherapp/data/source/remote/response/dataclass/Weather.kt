package com.example.weatherapp.data.source.remote.response.dataclass

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
