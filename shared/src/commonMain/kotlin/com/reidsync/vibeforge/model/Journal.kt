package com.reidsync.vibeforge.model

import com.reidsync.vibeforge.primitives.uuid.UUID
import kotlinx.datetime.LocalDateTime

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

data class Journal(
	val id: UUID,
	val title: String,
	val date: LocalDateTime,
	val contents: String
) {
	companion object
}

