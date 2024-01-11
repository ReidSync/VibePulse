package com.reidsync.vibepulse.notebook.journal

import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2024/01/10.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

@Serializable
enum class Feelings(val displayName: String) {
	Sad("Sad"),
	Angry("Angry"),
	Neutral("Okay"),
	Happy("Good"),
	SuperHappy("Awesome")
}