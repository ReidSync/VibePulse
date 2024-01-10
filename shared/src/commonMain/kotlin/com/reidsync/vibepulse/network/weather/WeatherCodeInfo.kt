package com.reidsync.vibepulse.network.weather

/**
 * Created by Reid on 2024/01/08.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

enum class WeatherCodeInfo(val code: Int, val description: String= "Unknown", val isDay: Int = 1) {
	Unknown(-100, "Unknown", 1),
	// Clear sky
	CLEAR_SKY_DAY(0, "Clear sky", 1),
	CLEAR_SKY_NIGHT(0, "Clear sky", 0),

	// Mainly clear, partly cloudy, and overcast
	MAINLY_CLEAR_DAY(1, "Mainly clear",1),
	PARTLY_CLOUDY_DAY(2, "Partly cloudy", 1),
	OVERCAST_DAY(3, "Overcast", 1),

	MAINLY_CLEAR_NIGHT(1, "Mainly clear", 0),
	PARTLY_CLOUDY_NIGHT(2, "Partly cloudy", 0),
	OVERCAST_NIGHT(3, "Overcast", 0),

	// Fog and depositing rime fog
	FOG_AND_DEPOSITING_RIME_FOG_DAY(45, "Fog and depositing rime fog", 1),
	FOG_AND_DEPOSITING_RIME_FOG_NIGHT(45, "Fog and depositing rime fog", 0),

	// Drizzle: Light, moderate, and dense intensity
	DRIZZLE_LIGHT_DAY(51, "Drizzle: Light", 1),
	DRIZZLE_MODERATE_DAY(53, "Drizzle: Moderate", 1),
	DRIZZLE_DENSE_DAY(55, "Drizzle: Dense", 1),
	DRIZZLE_LIGHT_NIGHT(51, "Drizzle: Light", 0),
	DRIZZLE_MODERATE_NIGHT(53, "Drizzle: Moderate", 0),
	DRIZZLE_DENSE_NIGHT(55, "Drizzle: Dense", 0),

	// Freezing Drizzle: Light and dense intensity
	FREEZING_DRIZZLE_LIGHT_DAY(56, "Freezing Drizzle: Light", 1),
	FREEZING_DRIZZLE_DENSE_DAY(57, "Freezing Drizzle: Dense", 1),
	FREEZING_DRIZZLE_LIGHT_NIGHT(56, "Freezing Drizzle: Light", 0),
	FREEZING_DRIZZLE_DENSE_NIGHT(57, "Freezing Drizzle: Dense", 0),

	// Rain: Slight, moderate and heavy intensity
	RAIN_SLIGHT_DAY(61, "Rain: Slight", 1),
	RAIN_MODERATE_DAY(63, "Rain: Moderate", 1),
	RAIN_HEAVY_DAY(65, "Rain: Heavy", 1),
	RAIN_SLIGHT_NIGHT(61, "Rain: Slight", 0),
	RAIN_MODERATE_NIGHT(63, "Rain: Moderate", 0),
	RAIN_HEAVY_NIGHT(65, "Rain: Heavy", 0),

	// Freezing Rain: Light and heavy intensity
	FREEZING_RAIN_LIGHT_DAY(66, "Freezing Rain: Light", 1),
	FREEZING_RAIN_HEAVY_DAY(67, "Freezing Rain: Heavy", 1),
	FREEZING_RAIN_LIGHT_NIGHT(66, "Freezing Rain: Light", 0),
	FREEZING_RAIN_HEAVY_NIGHT(67, "Freezing Rain: Heavy", 0),

	// Snow fall: Slight, moderate, and heavy intensity

	SNOW_FALL_SLIGHT_DAY(71, "Snow fall: Slight", 1),
	SNOW_FALL_SLIGHT_NIGHT(71, "Snow fall: Slight", 0);

	companion object {
		private val map = entries.associateBy { Pair(it.code, it.isDay) }
		infix fun from(value: Pair<Int, Int>) = map[value]
//		infix fun from(value: Pair<Int, Int>): WeatherCodeInfo?
//				= entries.firstOrNull { it.code == value.first && it.isDay == value.second }

	}
}

//// (\w+)([A-Z])(\() -> \L$1$2(
// \b([A-Z])(\w+)([A-Z])(\() -> \U$1\L$2$3(
// (\w)[_]{1,1}([a-z]) -> $1\U$2