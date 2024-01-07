package com.reidsync.vibepulse.notebook.journal

import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2024/01/04.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */


@Serializable
data class JournalLocation(
	val latitude: Double = 0.0,
	val longitude: Double = 0.0,
	val cityName: String = ""
)

expect fun JournalLocation.getString(): String