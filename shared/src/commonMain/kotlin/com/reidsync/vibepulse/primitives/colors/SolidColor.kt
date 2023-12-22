package com.reidsync.vibepulse.primitives.colors

import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2023/12/22.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Serializable
data class SolidColor(
	val r: Float,
	val g: Float,
	val b: Float,
	val a: Float
) {
	companion object {
		val Red = SolidColor(1f,0f,0f,1f)
		val Green = SolidColor(0f,1f,0f,1f)
		val Blue = SolidColor(0f,0f,1f,1f)
		val Cyan = SolidColor(0f,1f,1f,1f)
		val Magenta = SolidColor(1f,0f,1f,1f)
		val Yellow = SolidColor(1f,1f,0f,1f)
		val Black = SolidColor(0f,0f,0f,1f)
		val White = SolidColor(1f,1f,1f,1f)
		val VeryDarkGray = SolidColor(0.15f,0.15f,0.15f,1f)
		val DarkerGray = SolidColor(0.25f,0.25f,0.25f,1f)
		val DarkGray = SolidColor(0.3333f,0.3333f,0.3333f,1f)
		val MidGray = SolidColor(0.5f,0.5f,0.5f,1f)
		val LightGray = SolidColor(0.6666f,0.6666f,0.6666f,1f)
		val LighterGray = SolidColor(0.75f,0.75f,0.75f,1f)
		val VeryLightGray = SolidColor(0.85f,0.85f,0.85f,1f)

		val PeriwinkleA = SolidColor(154f, 156f, 234f, 1f)
		val PeriwinkleB = SolidColor(162f, 185f, 238f, 1f)
		val PeriwinkleC = SolidColor(162f, 220f, 238f, 1f)
		val PeriwinkleD = SolidColor(173f, 238f, 226f, 1f)

		val SunsetA = SolidColor(53f, 92f, 125f, 1f)
		val SunsetB = SolidColor(114f, 90f, 122f, 1f)
		val SunsetC = SolidColor(197f, 108f, 134f, 1f)
		val SunsetD = SolidColor(255f, 117f, 130f, 1f)
	}
}