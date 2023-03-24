package com.example.weatherapp.data.source.remote.response.dataclass

import com.google.gson.annotations.SerializedName

data class Main(
    var temp: Double,
    var feels_alike: Double,
    @SerializedName("temp_min")
    var minTemp: Double,
    @SerializedName("temp_max")
    var maxTemp: Double,
    var pressure: Int,
    @SerializedName("sea_level")
    var seaLevel: Int,
    @SerializedName("grnd_level")
    var groundLevel: Int,
    var humidity: Int,


)