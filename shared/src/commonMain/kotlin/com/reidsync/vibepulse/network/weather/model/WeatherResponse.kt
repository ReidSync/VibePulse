package com.reidsync.vibepulse.network.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val current: Current,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    @SerialName("elevation")
    val elevation: Double,
    @SerialName("generationtime_ms")
    val generationtimeMs: Double,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int
)