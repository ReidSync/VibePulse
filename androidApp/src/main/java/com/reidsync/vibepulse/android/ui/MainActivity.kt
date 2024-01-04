package com.reidsync.vibepulse.android.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reidsync.vibepulse.android.AppThemeColor
import com.reidsync.vibepulse.android.util.MyApplicationTheme
import com.reidsync.vibepulse.android.data.conventions.toColor
import com.reidsync.vibepulse.android.ui.appfeature.AppFeatureScreen
import com.reidsync.vibepulse.android.ui.appfeature.AppFeatureViewModel
import com.reidsync.vibepulse.primitives.colors.RC
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			var locationPermissionsGranted by remember { mutableStateOf(areLocationPermissionsAlreadyGranted()) }
			var shouldShowPermissionRationale by remember {
				mutableStateOf(
					shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
				)
			}

			var shouldDirectUserToApplicationSettings by remember {
				mutableStateOf(false)
			}

			var currentPermissionsStatus by remember {
				mutableStateOf(decideCurrentPermissionStatus(locationPermissionsGranted, shouldShowPermissionRationale))
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
							shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
					}
					shouldDirectUserToApplicationSettings = !shouldShowPermissionRationale && !locationPermissionsGranted
					currentPermissionsStatus = decideCurrentPermissionStatus(locationPermissionsGranted, shouldShowPermissionRationale)
				})

			val lifecycleOwner = LocalLifecycleOwner.current
			DisposableEffect(key1 = lifecycleOwner, effect = {
				val observer = LifecycleEventObserver { _, event ->
					if (event == Lifecycle.Event.ON_START &&
						!locationPermissionsGranted &&
						!shouldShowPermissionRationale) {
						locationPermissionLauncher.launch(locationPermissions)
					}
				}
				lifecycleOwner.lifecycle.addObserver(observer)
				onDispose {
					lifecycleOwner.lifecycle.removeObserver(observer)
				}
			})

			val scope = rememberCoroutineScope()
			val snackbarHostState = remember { SnackbarHostState() }

			MyApplicationTheme {
				CompositionLocalProvider(
					AppThemeColor provides if (isSystemInDarkTheme()) {
						RC.dark
					} else {
						RC.light
					}
				) {
					Surface(
						modifier = Modifier.fillMaxSize(),
						color = AppThemeColor.current.vibePulseColors.background.toColor()
					) {
						Scaffold(snackbarHost = {
							SnackbarHost(hostState = snackbarHostState)
						}) { contentPadding ->
							AppFeatureScreen(
								viewModel(factory = AppFeatureViewModel.Factory)
							)

							if (shouldShowPermissionRationale) {
								LaunchedEffect(Unit) {
									scope.launch {
										val userAction = snackbarHostState.showSnackbar(
											message ="Please authorize location permissions",
											actionLabel = "Approve",
											duration = SnackbarDuration.Indefinite,
											withDismissAction = true
										)
										when (userAction) {
											SnackbarResult.ActionPerformed -> {
												shouldShowPermissionRationale = false
												locationPermissionLauncher.launch(locationPermissions)
											}
											SnackbarResult.Dismissed -> {
												shouldShowPermissionRationale = false
											}
										}
									}
								}
							}
							if (shouldDirectUserToApplicationSettings) {
								openApplicationSettings()
							}
						}
					}
				}
			}
		}
	}

	private fun areLocationPermissionsAlreadyGranted(): Boolean {
		return ContextCompat.checkSelfPermission(
			this,
			Manifest.permission.ACCESS_FINE_LOCATION
		) == PackageManager.PERMISSION_GRANTED
	}

	private fun openApplicationSettings() {
		Intent(
			Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
			Uri.fromParts("package", packageName, null)
		).also {
			startActivity(it)
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
}
