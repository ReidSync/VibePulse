package com.reidsync.vibepulse.android.ui.meta.location

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.reidsync.vibepulse.android.AppThemeColor
import com.reidsync.vibepulse.android.data.conventions.toColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Reid on 2024/01/04.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */


// https://developer.android.com/develop/sensors-and-location/location/retrieve-current
@Composable
fun CurrentLocationContent(usePreciseLocation: Boolean) {
	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	val locationClient = remember {
		LocationServices.getFusedLocationProviderClient(context)
	}
	var locationInfo by remember {
		mutableStateOf("")
	}

	Row(
		Modifier
			.fillMaxWidth()
			.animateContentSize()
	) {
		Text(
			text = locationInfo,
			fontSize = 16.sp,
			color = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
			modifier = Modifier
				.padding(4.dp)
			)

		Button(
			onClick = {
				//To get more accurate or fresher device location use this method
				scope.launch(Dispatchers.IO) {
					val priority = if (usePreciseLocation) {
						Priority.PRIORITY_HIGH_ACCURACY
					} else {
						Priority.PRIORITY_BALANCED_POWER_ACCURACY
					}

					if (ActivityCompat.checkSelfPermission(
							context,
							Manifest.permission.ACCESS_FINE_LOCATION
						) == PackageManager.PERMISSION_GRANTED ||
						ActivityCompat.checkSelfPermission(
							context,
							Manifest.permission.ACCESS_COARSE_LOCATION
						) == PackageManager.PERMISSION_GRANTED
					) {
						locationClient.getCurrentLocation(
							priority,
							CancellationTokenSource().token,
						).addOnSuccessListener { fetchedLocation ->
							locationInfo = String.format("(%.2f, %.2f)", fetchedLocation.latitude, fetchedLocation.longitude)
						}
					}
				}
			},
		) {
			Text(text = "re")
		}
//		Button(
//			onClick = {
//				scope.launch(Dispatchers.IO) {
//					if (ActivityCompat.checkSelfPermission(
//							context,
//							Manifest.permission.ACCESS_FINE_LOCATION
//						) == PackageManager.PERMISSION_GRANTED ||
//						ActivityCompat.checkSelfPermission(
//							context,
//							Manifest.permission.ACCESS_COARSE_LOCATION
//						) == PackageManager.PERMISSION_GRANTED
//					) {
//						locationClient.lastLocation
//							.addOnSuccessListener { result ->
//								locationInfo = if (result == null) {
//									"No last known location. Try fetching the current location first"
//								} else {
//									"Current location is \n" + "lat : ${result.latitude}\n" +
//											"long : ${result.longitude}\n" + "fetched at ${System.currentTimeMillis()}"
//								}
//							}
//					} else {
//						// ToDo. Request location permissions.
//					}
//				}
//			},
//		) {
//			Text("Get last known location")
//		}
	}
}