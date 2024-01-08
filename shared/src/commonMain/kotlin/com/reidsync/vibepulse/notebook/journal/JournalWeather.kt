package com.reidsync.vibepulse.notebook.journal

import com.reidsync.vibepulse.network.weather.model.WeatherResponse
import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2024/01/08.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

@Serializable
data class JournalWeather(
	val weatherCode: Int = 0
)

fun WeatherResponse.asJournalWeather(): JournalWeather {
	return JournalWeather(
		weatherCode = this.current.weatherCode
	)
}