package com.reidsync.vibepulse.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import io.ktor.serialization.kotlinx.json.*

/**
 * Created by Reid on 2024/01/02.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

@kotlinx.serialization.Serializable
data class WeatherResponse(
	@SerialName("main") val main: Main,
	@SerialName("weather") val weather: List<Weather>,
	@SerialName("name") val cityName: String
)

@kotlinx.serialization.Serializable
data class Main(
	@SerialName("temp") val temperature: Double,
	@SerialName("humidity") val humidity: Int
)

@Serializable
data class Weather(
	@SerialName("description") val description: String
)

class VibePulseHttpClient {
//	private val client = HttpClient()
//
//	suspend fun greeting(): String {
//		val response = client.get("https://ktor.io/docs/")
//		return response.bodyAsText()
//	}

//	suspend fun fetchWeather(): String {
//		return client.get<String> {
//			url("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
//		}
//	}

	//suspend fun getWeather(apiKey: String, city: String): WeatherResponse {
	suspend fun getWeather()  {
		val client = HttpClient(CIO) {
			// https://ktor.io/docs/serialization.html
			install(ContentNegotiation) {
				json(Json {
					prettyPrint = true
					isLenient = true
				})
			}
		}

		val city = "London,uk"
		val apiKey = ""

		val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey"
		val result = client.get(url)
		println(result)
		//return result
	}
}