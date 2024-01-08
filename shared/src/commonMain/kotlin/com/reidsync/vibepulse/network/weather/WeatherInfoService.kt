package com.reidsync.vibepulse.network.weather

import com.reidsync.vibepulse.network.weather.model.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*

/**
 * Created by Reid on 2024/01/02.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

class WeatherInfoService {
	companion object {
		private const val BASE_URL = "https://api.open-meteo.com/v1/forecast"
		private const val WEATHER_CODE = "weather_code"
		private const val CURRENT = "current"
		private const val TEMP = "temperature_2m"
		private const val LATITUDE = "latitude"
		private const val LONGITUDE = "longitude"
		private const val IS_DAY = "is_day"
		//current=temperature_2m,is_day,weather_code
		private const val CURRENT_INFO = "$CURRENT=$TEMP,$IS_DAY,$WEATHER_CODE"
	}

	private val client by lazy {
		HttpClient {
			install(ContentNegotiation) {
				json()
			}

			install(HttpTimeout) {
				requestTimeoutMillis = 3000
			}
		}
	}

	suspend fun getWeatherResponse(latitude: Double, longitude: Double): WeatherResponse {
		val url = apiURL(latitude, longitude)
		println("weather request URL : $url")
		return client.get(url).let {
			it.bodyAsText().also { json -> println("VibePulseHttpClient: $json") }
			it.body()
		}
	}

	private fun apiURL(
		latitude: Double,
		longitude: Double
	) = "$BASE_URL?$LATITUDE=$latitude&$LONGITUDE=$longitude&$CURRENT_INFO"
	//https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,is_day,weather_code
	//https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true
}