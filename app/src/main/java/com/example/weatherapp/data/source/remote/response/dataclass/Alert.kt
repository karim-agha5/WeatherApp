package com.example.weatherapp.data.source.remote.response.dataclass

import com.google.gson.annotations.SerializedName

data class Alert(
    @SerializedName("sender_name")
    val senderName: String,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String,
    val tags: List<String>
)
