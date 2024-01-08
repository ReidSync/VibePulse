package com.reidsync.vibepulse.android.data.repositories.impl

import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.reidsync.vibepulse.android.data.repositories.LocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

/**
 * Created by Reid on 2024/01/08.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

class LocationRepositoryImpl constructor(private val context: Context): LocationRepository {
	private val geocoder = Geocoder(context, Locale.getDefault())

	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	override suspend fun getAddress(latitude: Double, longitude: Double): Result<String> {
		val maxResult = 1
		return suspendCancellableCoroutine { continuation ->
			geocoder.getFromLocation(
				latitude, longitude, maxResult
				//48.858568, 2.294513, 1 // Paris.
			) { addresses ->
				if (addresses.isNotEmpty()) {
					continuation.resume(Result.success(addresses[0].locality ?: "Unknown City"))
				} else {
					continuation.resume(Result.success("Unknown City"))
				}
			}
		}
	}

}