package com.reidsync.vibepulse.primitives.colors

import android.content.res.Configuration
import android.content.res.Resources

/**
 * Created by Reid on 2023/12/23.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

actual class ThemeColor actual constructor() {
	actual val background: SolidColor
		get() = if (isDarkMode()) SolidColor.lightBackground else SolidColor.darkBackground

	actual val vibeA: SolidColor
		get() = SolidColor.PeriwinkleA
	actual val vibeB: SolidColor
		get() = SolidColor.SunsetA
	actual val vibeC: SolidColor
		get() = SolidColor.SunsetB
	actual val vibeD: SolidColor
		get() = SolidColor.CandleB

	// This function works properly. But should consider about a performance.
	// Is it okay to get a color using the function call checking its uiMode every time?
	private fun isDarkMode(): Boolean {
		val resources = Resources.getSystem()
		val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
		return currentNightMode == Configuration.UI_MODE_NIGHT_YES
	}
}