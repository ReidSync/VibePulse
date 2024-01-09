package com.reidsync.vibepulse.network.weather

/**
 * Created by Reid on 2024/01/08.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

enum class WeatherCodeInfo(val code: Int, val description: String= "Unknown", val isDay: Int = 1) {
	Unknown(-100, "Unknown", 1),
	// Clear sky
	ClearSkyDay(0, "Clear sky", 1),
	ClearSkyNight(0, "Clear sky", 0),

	// Mainly clear, partly cloudy, and overcast
	MainlyClearDay(1, "Mainly clear", 1),
	PartlyCloudyDay(2, "Partly cloudy", 1),
	OvercastDay(3, "Overcast", 1),

	MainlyClearNight(1, "Mainly clear", 0),
	PartlyCloudyNight(2, "Partly cloudy", 0),
	OvercastNight(3, "Overcast", 0),

	// Fog and depositing rime fog
	FogAndDepositingRimeFogDay(45, "Fog and depositing rime fog", 1),
	FogAndDepositingRimeFogNight(45, "Fog and depositing rime fog", 0),

	// Drizzle: Light, moderate, and dense intensity
	DrizzleLightDay(51, "Drizzle: Light", 1),
	DrizzleModerateDay(53, "Drizzle: Moderate", 1),
	DrizzleDenseDay(55, "Drizzle: Dense", 1),
	DrizzleLightNight(51, "Drizzle: Light", 0),
	DrizzleModerateNight(53, "Drizzle: Moderate", 0),
	DrizzleDenseNight(55, "Drizzle: Dense", 0),

	// Freezing Drizzle: Light and dense intensity
	FreezingDrizzleLightDay(56, "Freezing Drizzle: Light", 1),
	FreezingDrizzleDenseDay(57, "Freezing Drizzle: Dense", 1),
	FreezingDrizzleLightNight(56, "Freezing Drizzle: Light", 0),
	FreezingDrizzleDenseNight(57, "Freezing Drizzle: Dense", 0),

	// Rain: Slight, moderate and heavy intensity
	RainSlightDay(61, "Rain: Slight", 1),
	RainModerateDay(63, "Rain: Moderate", 1),
	RainHeavyDay(65, "Rain: Heavy", 1),
	RainSlightNight(61, "Rain: Slight", 0),
	RainModerateNight(63, "Rain: Moderate", 0),
	RainHeavyNight(65, "Rain: Heavy", 0),

	// Freezing Rain: Light and heavy intensity
	FreezingRainLightDay(66, "Freezing Rain: Light", 1),
	FreezingRainHeavyDay(67, "Freezing Rain: Heavy", 1),
	FreezingRainLightNight(66, "Freezing Rain: Light", 0),
	FreezingRainHeavyNight(67, "Freezing Rain: Heavy", 0),

	// Snow fall: Slight, moderate, and heavy intensity
	SnowFallSlightDay(71, "Snow fall: Slight", 1),
	SnowFallSlightNight(71, "Snow fall: Slight", 0)
}

//// (\w+)([A-Z])(\() -> \L$1$2(
// \b([A-Z])(\w+)([A-Z])(\() -> \U$1\L$2$3(
// (\w)[_]{1,1}([a-z]) -> $1\U$2