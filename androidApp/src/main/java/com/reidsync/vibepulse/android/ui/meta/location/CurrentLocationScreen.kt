package com.reidsync.vibepulse.android.ui.meta.location

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun CurrentLocationContent(
	requestPermissions: () -> Unit,
	update: (Double, Double) -> Unit,
	usePreciseLocation: Boolean
) {
	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	val locationClient = remember {
		LocationServices.getFusedLocationProviderClient(context)
	}

	Row(
		Modifier
			.animateContentSize()
	) {
//		Button(
//			onClick = {
//				requestPermissions()
//				scope.launch(Dispatchers.IO) {
//					val priority = if (usePreciseLocation) {
//						Priority.PRIORITY_HIGH_ACCURACY
//					} else {
//						Priority.PRIORITY_BALANCED_POWER_ACCURACY
//					}
//
//					if (ActivityCompat.checkSelfPermission(
//							context,
//							Manifest.permission.ACCESS_FINE_LOCATION
//						) == PackageManager.PERMISSION_GRANTED ||
//						ActivityCompat.checkSelfPermission(
//							context,
//							Manifest.permission.ACCESS_COARSE_LOCATION
//						) == PackageManager.PERMISSION_GRANTED
//					) {
//						locationClient.getCurrentLocation(
//							priority,
//							CancellationTokenSource().token,
//						).addOnSuccessListener { fetchedLocation ->
//							update(fetchedLocation.latitude, fetchedLocation.longitude)
//							//locationInfo = String.format("(%.2f, %.2f)", fetchedLocation.latitude, fetchedLocation.longitude)
//						}
//					}
//				}
//			},
//		) {
//			Text(
//				text = "Refresh",
//				modifier = Modifier
//					.background(AppThemeColor.current.vibePulseColors.vibeA.toColor()),
//			)
//		}
		Box(
			modifier = Modifier
				.width(80.dp)
				.clip(RoundedCornerShape(20.dp))
				.background(
					color = AppThemeColor.current.vibePulseColors.vibeA.toColor(),
					shape = RoundedCornerShape(20.dp)
				)
				.clickable(
					interactionSource = remember { MutableInteractionSource() },
					indication = rememberRipple(
						bounded = true,
						color = AppThemeColor.current.vibePulseColors.vibeC.toColor()
					),
					onClick = {
						requestPermissions()
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
									update(fetchedLocation.latitude, fetchedLocation.longitude)
									//locationInfo = String.format("(%.2f, %.2f)", fetchedLocation.latitude, fetchedLocation.longitude)
								}
							}
						}
					}
				)
				.padding(top = 10.dp, bottom = 10.dp)
		) {
			Text(
				text = "Refresh",
				fontSize = 13.sp,
				color = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
				modifier = Modifier
					.align(Alignment.Center)
					.padding(4.dp),

				)
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