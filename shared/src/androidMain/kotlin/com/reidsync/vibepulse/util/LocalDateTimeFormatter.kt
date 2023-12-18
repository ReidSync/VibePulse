package com.reidsync.vibepulse.util

import java.time.format.DateTimeFormatter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime

/**
 * Created by Reid on 2023/12/18.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


actual fun LocalDateTime.format(
	format: String
): String = DateTimeFormatter
	.ofPattern(format)
	.format(this.toJavaLocalDateTime())