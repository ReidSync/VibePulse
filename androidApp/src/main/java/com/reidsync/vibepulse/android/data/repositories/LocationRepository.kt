package com.reidsync.vibepulse.android.data.repositories

/**
 * Created by Reid on 2024/01/08.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

interface LocationRepository {
	suspend fun getAddress(latitude: Double, longitude: Double): Result<String>
}