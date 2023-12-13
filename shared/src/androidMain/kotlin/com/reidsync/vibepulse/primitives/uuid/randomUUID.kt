package com.reidsync.vibepulse.primitives.uuid

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

actual fun UUID.Companion.randomUUID(): UUID {
	val uuid = java.util.UUID.randomUUID()
	return UUID(uuid.mostSignificantBits.toULong(),uuid.leastSignificantBits.toULong())
}