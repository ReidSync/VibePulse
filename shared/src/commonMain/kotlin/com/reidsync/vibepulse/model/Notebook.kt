package com.reidsync.vibepulse.model

import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2023/12/14.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Serializable
data class Notebook(
	val journals: List<Journal> = emptyList()
)