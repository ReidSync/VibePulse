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
		private fun withHex(color: Int, alpha: Float = 1.0f): SolidColor {
			val r = (color shr 16 and 0xFF) / 255f
			val g = (color shr 8 and 0xFF) / 255f
			val b = (color and 0xFF) / 255f
			//val a = (color shr 24 and 0xFF) / 255f
			return SolidColor(r, g, b, alpha)
		}

		private fun withDec(r: Int, g: Int, b: Int, a: Float = 1.0f): SolidColor =
			SolidColor(r/255f, g/255f, b/255f, a)

		val Red = SolidColor(1f,0f,0f,1f)
		val Green = SolidColor(0f,1f,0f,1f)
		val Blue = SolidColor(0f,0f,1f,1f)
		val Cyan = SolidColor(0f,1f,1f,1f)
		val Magenta = SolidColor(1f,0f,1f,1f)
		val Yellow = SolidColor(1f,1f,0f,1f)
		val Black = SolidColor(0f,0f,0f,1f)
		val White = SolidColor(1f,1f,1f,1f)
		val VeryDarkGray = SolidColor(0.16f,0.16f,0.17f,1f)
		val DarkerGray = SolidColor(0.25f,0.25f,0.25f,1f)
		val DarkGray = SolidColor(0.3333f,0.3333f,0.3333f,1f)
		val MidGray = SolidColor(0.5f,0.5f,0.5f,1f)
		val LightGray = SolidColor(0.6666f,0.6666f,0.6666f,1f)
		val LighterGray = SolidColor(0.75f,0.75f,0.75f,1f)
		val VeryLightGray = SolidColor(0.85f,0.85f,0.85f,1f)

		val PeriwinkleA = SolidColor.withHex(0x9A9CEA)
		val PeriwinkleB = SolidColor.withHex(0xA2B9EE)
		val PeriwinkleC = SolidColor.withHex(0xA2DCEE)
		val PeriwinkleD = SolidColor.withHex(0xADEEE2)

		val SeaSideA = SolidColor.withHex(0x26648E)
		val SeaSideB = SolidColor.withHex(0x4F8FC0)
		val SeaSideC = SolidColor.withHex(0x53D2DC)
		val SeaSideD = SolidColor.withHex(0xFFE3B3)

		val SunsetA = SolidColor.withHex(0x355C7D)
		val SunsetB = SolidColor.withHex(0x725A7A)
		val SunsetC = SolidColor.withHex(0xC56C86)
		val SunsetD = SolidColor.withHex(0xFF7582)

		val CandleA = SolidColor.withHex(0xFB8C6F)
		val CandleB = SolidColor.withHex(0x73607D)
		val CandleC = SolidColor.withHex(0xC1B9AE)
		val CandleD = SolidColor.withHex(0xFDC664)

		val LightBackground = SolidColor.withHex(0xF2F2F7) // 0xFFF2F2F7
		val DarkBackground = VeryDarkGray

		val LightListBackground = SolidColor.White
		val DarkListBackground = SolidColor.withDec(28, 28, 30)
	}
}