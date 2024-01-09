package com.reidsync.vibepulse.android.data

import com.reidsync.vibepulse.android.R
import com.reidsync.vibepulse.network.weather.WeatherCodeInfo
import com.reidsync.vibepulse.notebook.journal.Feelings

/**
 * Created by Reid on 2023/12/28.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

val FeelingEmojis = mapOf(
	Feelings.Sad to R.drawable.sad,
	Feelings.Angry to R.drawable.angry,
	Feelings.Neutral to R.drawable.neutral,
	Feelings.Happy to R.drawable.happy,
	Feelings.SuperHappy to R.drawable.super_happy
)

val WeatherIcons = mapOf(
	WeatherCodeInfo.Unknown to R.drawable.weather,

	WeatherCodeInfo.ClearSkyDay to R.drawable.day,
	WeatherCodeInfo.ClearSkyNight to R.drawable.night,

	WeatherCodeInfo.MainlyClearDay to R.drawable.cloudy_day_1,
	WeatherCodeInfo.PartlyCloudyDay to R.drawable.cloudy_day_2,
	WeatherCodeInfo.OvercastDay to R.drawable.cloudy_day_3,

	WeatherCodeInfo.MainlyClearNight to R.drawable.cloudy_night_1,
	WeatherCodeInfo.PartlyCloudyNight to R.drawable.cloudy_night_2,
	WeatherCodeInfo.OvercastNight to R.drawable.cloudy_night_3,

	WeatherCodeInfo.FogAndDepositingRimeFogDay to R.drawable.cloudy_day_3,
	WeatherCodeInfo.FogAndDepositingRimeFogNight to R.drawable.cloudy_night_3,

	WeatherCodeInfo.DrizzleLightDay to R.drawable.rainy_1,
	WeatherCodeInfo.DrizzleModerateDay to R.drawable.rainy_2,
	WeatherCodeInfo.DrizzleDenseDay to R.drawable.rainy_3,
	WeatherCodeInfo.DrizzleLightNight to R.drawable.rainy_4,
	WeatherCodeInfo.DrizzleModerateNight to R.drawable.rainy_5,
	WeatherCodeInfo.DrizzleDenseNight to R.drawable.rainy_6,

	WeatherCodeInfo.FreezingDrizzleLightDay to R.drawable.snowy_1,
	WeatherCodeInfo.FreezingDrizzleDenseDay to R.drawable.snowy_3,
	WeatherCodeInfo.FreezingDrizzleLightNight to R.drawable.snowy_4,
	WeatherCodeInfo.FreezingDrizzleDenseNight to R.drawable.snowy_6,

	WeatherCodeInfo.RainSlightDay to R.drawable.rainy_1,
	WeatherCodeInfo.RainModerateDay to R.drawable.rainy_2,
	WeatherCodeInfo.RainHeavyDay to R.drawable.rainy_3,
	WeatherCodeInfo.RainSlightNight to R.drawable.rainy_4,
	WeatherCodeInfo.RainModerateNight to R.drawable.rainy_5,
	WeatherCodeInfo.RainHeavyNight to R.drawable.rainy_6,

	WeatherCodeInfo.FreezingRainLightDay to R.drawable.snowy_1,
	WeatherCodeInfo.FreezingRainHeavyDay to R.drawable.snowy_3,
	WeatherCodeInfo.FreezingRainLightNight to R.drawable.snowy_4,
	WeatherCodeInfo.FreezingRainHeavyNight to R.drawable.snowy_6,


	WeatherCodeInfo.SnowFallSlightDay to R.drawable.snowy_1,
	WeatherCodeInfo.SnowFallSlightNight to R.drawable.snowy_1
)