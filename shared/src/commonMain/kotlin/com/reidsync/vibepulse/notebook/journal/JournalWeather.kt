package com.reidsync.vibepulse.notebook.journal

import com.reidsync.vibepulse.network.weather.WeatherCodeInfo
import com.reidsync.vibepulse.network.weather.model.WeatherResponse
import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2024/01/08.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

@Serializable
data class JournalWeather(
	val weatherInfo: WeatherCodeInfo = WeatherCodeInfo.Unknown
)

fun WeatherResponse.asJournalWeather(): JournalWeather {
	return JournalWeather(weatherInfo
	= (WeatherCodeInfo from Pair(current.weatherCode, current.isDay)) ?: WeatherCodeInfo.Unknown
	)
}