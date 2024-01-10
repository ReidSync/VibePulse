package com.reidsync.vibepulse.network.weather

import com.reidsync.vibepulse.network.weather.model.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json

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
			expectSuccess = true
			install(ContentNegotiation) {
				json()
			}

			install(HttpTimeout) {
				requestTimeoutMillis = 3000
			}

			HttpResponseValidator {
				handleResponseExceptionWithRequest { exception, request ->
					println(exception)
					throw exception
				}
			}
		}
	}

	suspend fun getWeatherResponse(latitude: Double, longitude: Double): Result<WeatherResponse> {
		val url = apiURL(latitude, longitude)
		println("weather request URL : $url")
		// The code below works, but it is different from my intended purpose.
		// KMP has a restriction - Suspend functions in the Shared module should be called in the Main thread from the iOS side.
		// So, I had to use the Main Actor to call this suspend function, and it causes a slight UI pause on iOS. (Of course)
		// I wrote the code below expecting this function to run in the IO thread.
		// It worked, but I couldn't avoid awaiting the result from the function on the Main Thread of the iOS side.
		// As a result, I had to add `kotlin.native.binary.objcExportSuspendFunctionLaunchThreadRestriction=none` to `gradle.properties`
		// ref: https://youtrack.jetbrains.com/issue/KT-51297/Native-allow-calling-Kotlin-suspend-functions-on-non-main-thread-from-Swift
//		return suspendCoroutine<WeatherResponse> { continuation ->
//			GlobalScope.launch(Dispatchers.IO) {
//				client.get(url).let {
//					it.bodyAsText().also { json -> println("VibePulseHttpClient: $json") }
//					continuation.resume(it.body())
//				}
//			}
//		}

		// replace try-catch with runCatching
		return runCatching {
			client.get(url).let {
				it.bodyAsText().also { json -> println("VibePulseHttpClient: $json") }
				it.body()
			}
		}
	}

	private fun apiURL(
		latitude: Double,
		longitude: Double
	) = "$BASE_URL?$LATITUDE=$latitude&$LONGITUDE=$longitude&$CURRENT_INFO"
	//https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,is_day,weather_code
	//https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true
	// https://api.open-meteo.com/v1/forecast?latitude=37.32&longitude=127.09&current=temperature_2m%2Cis_day&hourly=temperature_2m&daily=temperature_2m_max%2Ctemperature_2m_min&timezone=Africa%2FCairo&forecast_days=1
	// https://api.open-meteo.com/v1/forecast?latitude=37.32&longitude=127.09&current=temperature_2m%2Cis_day&hourly=temperature_2m&daily=temperature_2m_max%2Ctemperature_2m_min&timezone=Japan&forecast_days=1&&current_weather=true
}