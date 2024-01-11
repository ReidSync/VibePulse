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

	WeatherCodeInfo.CLEAR_SKY_DAY to R.drawable.day,
	WeatherCodeInfo.CLEAR_SKY_NIGHT to R.drawable.night,

	WeatherCodeInfo.MAINLY_CLEAR_DAY to R.drawable.cloudy_day_1,
	WeatherCodeInfo.PARTLY_CLOUDY_DAY to R.drawable.cloudy_day_2,
	WeatherCodeInfo.OVERCAST_DAY to R.drawable.cloudy_day_3,

	WeatherCodeInfo.MAINLY_CLEAR_NIGHT to R.drawable.cloudy_night_1,
	WeatherCodeInfo.PARTLY_CLOUDY_NIGHT to R.drawable.cloudy_night_2,
	WeatherCodeInfo.OVERCAST_NIGHT to R.drawable.cloudy_night_3,

	WeatherCodeInfo.FOG_AND_DEPOSITING_RIME_FOG_DAY to R.drawable.cloudy_day_3,
	WeatherCodeInfo.FOG_AND_DEPOSITING_RIME_FOG_NIGHT to R.drawable.cloudy_night_3,

	WeatherCodeInfo.DRIZZLE_LIGHT_DAY to R.drawable.rainy_1,
	WeatherCodeInfo.DRIZZLE_MODERATE_DAY to R.drawable.rainy_2,
	WeatherCodeInfo.DRIZZLE_DENSE_DAY to R.drawable.rainy_3,
	WeatherCodeInfo.DRIZZLE_LIGHT_NIGHT to R.drawable.rainy_4,
	WeatherCodeInfo.DRIZZLE_MODERATE_NIGHT to R.drawable.rainy_5,
	WeatherCodeInfo.DRIZZLE_DENSE_NIGHT to R.drawable.rainy_6,

	WeatherCodeInfo.FREEZING_DRIZZLE_LIGHT_DAY to R.drawable.snowy_1,
	WeatherCodeInfo.FREEZING_DRIZZLE_DENSE_DAY to R.drawable.snowy_3,
	WeatherCodeInfo.FREEZING_DRIZZLE_LIGHT_NIGHT to R.drawable.snowy_4,
	WeatherCodeInfo.FREEZING_DRIZZLE_DENSE_NIGHT to R.drawable.snowy_6,

	WeatherCodeInfo.RAIN_SLIGHT_DAY to R.drawable.rainy_1,
	WeatherCodeInfo.RAIN_MODERATE_DAY to R.drawable.rainy_2,
	WeatherCodeInfo.RAIN_HEAVY_DAY to R.drawable.rainy_3,
	WeatherCodeInfo.RAIN_SLIGHT_NIGHT to R.drawable.rainy_4,
	WeatherCodeInfo.RAIN_MODERATE_NIGHT to R.drawable.rainy_5,
	WeatherCodeInfo.RAIN_HEAVY_NIGHT to R.drawable.rainy_6,

	WeatherCodeInfo.FREEZING_RAIN_LIGHT_DAY to R.drawable.snowy_1,
	WeatherCodeInfo.FREEZING_RAIN_HEAVY_DAY to R.drawable.snowy_3,
	WeatherCodeInfo.FREEZING_RAIN_LIGHT_NIGHT to R.drawable.snowy_4,
	WeatherCodeInfo.FREEZING_RAIN_HEAVY_NIGHT to R.drawable.snowy_6,


	WeatherCodeInfo.SNOW_FALL_SLIGHT_DAY to R.drawable.snowy_1,
	WeatherCodeInfo.SNOW_FALL_SLIGHT_NIGHT to R.drawable.snowy_1
)