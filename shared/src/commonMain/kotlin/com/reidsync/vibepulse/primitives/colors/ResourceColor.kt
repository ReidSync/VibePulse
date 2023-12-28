package com.reidsync.vibepulse.primitives.colors

/**
 * Created by Reid on 2023/12/24.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

interface CommonColor {
	val background: SolidColor
	val listBackground: SolidColor
	val oppositBackground: SolidColor
}

interface VibePulseColor: CommonColor {
	val vibeA: SolidColor
	val vibeB: SolidColor
	val vibeC: SolidColor
	val vibeD: SolidColor
}

data class AppColor(
	val vibePulseColors: VibePulseColor
)

class ResourceColor {
	val dark = AppColor (
		object: VibePulseColor {
			override val vibeA: SolidColor
				get() = SolidColor.PeriwinkleA
			override val vibeB: SolidColor
				get() = SolidColor.SunsetA
			override val vibeC: SolidColor
				get() = SolidColor.SunsetB
			override val vibeD: SolidColor
				get() = SolidColor.CandleB
			override val background: SolidColor
				get() = SolidColor.DarkBackground
			override val listBackground: SolidColor
				get() = SolidColor.DarkListBackground
			override val oppositBackground: SolidColor
				get() = SolidColor.LightBackground
		}
	)

	val light = AppColor (
		object: VibePulseColor {
			override val vibeA: SolidColor
				get() = SolidColor.PeriwinkleA
			override val vibeB: SolidColor
				get() = SolidColor.SunsetA
			override val vibeC: SolidColor
				get() = SolidColor.SunsetB
			override val vibeD: SolidColor
				get() = SolidColor.CandleB
			override val background: SolidColor
				get() = SolidColor.LightBackground
			override val listBackground: SolidColor
				get() = SolidColor.LightListBackground
			override val oppositBackground: SolidColor
				get() = SolidColor.DarkBackground
		}
	)

	val default = dark
}

val RC: ResourceColor = ResourceColor()

