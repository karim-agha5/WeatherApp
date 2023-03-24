package com.example.weatherapp.data.source.remote.response.dataclass

data class City(
    var id: Int,
    var name: String,
    var coord: Coord,
    var country: String,
    var population: Int,
    var timezone: Int,
    var sunrise: Long,
    var sunset: Long
)
