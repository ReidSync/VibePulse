package com.reidsync.vibepulse.primitives.colors

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2023/12/23.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


expect class ThemeColor() {
	val background: SolidColor
	val vibeA: SolidColor
	val vibeB: SolidColor
	val vibeC: SolidColor
	val vibeD: SolidColor
}