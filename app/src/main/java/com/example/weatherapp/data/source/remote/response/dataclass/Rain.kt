package com.example.weatherapp.data.source.remote.response.dataclass

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    var oneHour: Double
)
