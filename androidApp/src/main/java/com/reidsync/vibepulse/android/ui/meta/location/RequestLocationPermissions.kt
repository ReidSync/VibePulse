package com.reidsync.vibepulse.android.ui.meta.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.reidsync.vibepulse.android.ui.common.findActivity

/**
 * Created by Reid on 2024/01/04.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

@Composable
fun CheckLocationPermission() {
	val context = LocalContext.current

	var locationPermissionsGranted by remember {
		mutableStateOf(
			areLocationPermissionsAlreadyGranted(
				context
			)
		)
	}
	var shouldShowPermissionRationale by remember {
		mutableStateOf(
			context.findActivity()
				.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
		)
	}

	var shouldDirectUserToApplicationSettings by remember {
		mutableStateOf(false)
	}

	var currentPermissionsStatus by remember {
		mutableStateOf(
			decideCurrentPermissionStatus(
				locationPermissionsGranted,
				shouldShowPermissionRationale
			)
		)
	}

	val locationPermissions = arrayOf(
		Manifest.permission.ACCESS_FINE_LOCATION,
		Manifest.permission.ACCESS_COARSE_LOCATION
	)

	val locationPermissionLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestMultiplePermissions(),
		onResult = { permissions ->
			locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
				acc && isPermissionGranted
			}

			if (!locationPermissionsGranted) {
				shouldShowPermissionRationale =
					context.findActivity()
						.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
			}
			shouldDirectUserToApplicationSettings =
				!shouldShowPermissionRationale && !locationPermissionsGranted
			currentPermissionsStatus = decideCurrentPermissionStatus(
				locationPermissionsGranted,
				shouldShowPermissionRationale
			)
		})

	val lifecycleOwner = LocalLifecycleOwner.current
	DisposableEffect(key1 = lifecycleOwner, effect = {
		val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_START &&
				!locationPermissionsGranted &&
				!shouldShowPermissionRationale
			) {
				locationPermissionLauncher.launch(locationPermissions)
			}
		}
		lifecycleOwner.lifecycle.addObserver(observer)
		onDispose {
			lifecycleOwner.lifecycle.removeObserver(observer)
		}
	})

	if (shouldShowPermissionRationale) {
		AlertDialog(
			onDismissRequest = {
			},
			title = {
				Text(text = "Location")
			},
			text = {
				Text("Please authorize location permissions")
			},
			confirmButton = {
				Button(
					onClick = {
						shouldShowPermissionRationale = false
						locationPermissionLauncher.launch(locationPermissions)
					}) {
					Text("Approve")
				}
			},
			dismissButton = {
				Button(
					onClick = {
						shouldShowPermissionRationale = false
					}) {
					Text("Deny")
				}
			}
		)
	}
	if (shouldDirectUserToApplicationSettings) {
		openApplicationSettings(context)
	}
}

private fun areLocationPermissionsAlreadyGranted(context: Context): Boolean {
	return ContextCompat.checkSelfPermission(
		context,
		Manifest.permission.ACCESS_FINE_LOCATION
	) == PackageManager.PERMISSION_GRANTED
}

private fun openApplicationSettings(context: Context) {
	Intent(
		Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
		Uri.fromParts("package", context.packageName, null)
	).also {
		context.startActivity(it)
	}
}

private fun decideCurrentPermissionStatus(
	locationPermissionsGranted: Boolean,
	shouldShowPermissionRationale: Boolean
): String {
	return if (locationPermissionsGranted) "Granted"
	else if (shouldShowPermissionRationale) "Rejected"
	else "Denied"
}