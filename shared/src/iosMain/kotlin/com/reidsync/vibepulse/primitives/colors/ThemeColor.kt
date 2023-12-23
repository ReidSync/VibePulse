package com.reidsync.vibepulse.primitives.colors

import kotlinx.serialization.Serializable
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.currentTraitCollection

/**
 * Created by Reid on 2023/12/23.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

import platform.UIKit.*

@Serializable
actual class ThemeColor actual constructor() {
	actual val background: SolidColor
		get() = if (isDarkMode()) SolidColor.darkBackground else SolidColor.lightBackground

	actual val vibeA: SolidColor
		get() = SolidColor.PeriwinkleA
	actual val vibeB: SolidColor
		get() = SolidColor.SunsetA
	actual val vibeC: SolidColor
		get() = SolidColor.SunsetB
	actual val vibeD: SolidColor
		get() = SolidColor.CandleB

	private fun isDarkMode(): Boolean {
		val currentTraitCollection = UIScreen.mainScreen.traitCollection
		//println("--isDark? ${currentTraitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark} / ${currentTraitCollection.userInterfaceStyle}")
		return currentTraitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark

	}
}