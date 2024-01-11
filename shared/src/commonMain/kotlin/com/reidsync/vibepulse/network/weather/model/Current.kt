package com.reidsync.vibepulse.network.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("interval")
    val interval: Int,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("temperature_2m")
    val temperature2m: Double,
    @SerialName("time")
    val time: String,
    @SerialName("weather_code")
    val weatherCode: Int
)