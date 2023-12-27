package com.reidsync.vibepulse.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reidsync.vibepulse.android.data.conventions.toColor
import com.reidsync.vibepulse.android.ui.AppFeatureScreen
import com.reidsync.vibepulse.android.viewModel.AppFeatureViewModel
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.primitives.colors.RC

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
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
						AppFeatureScreen(
							viewModel(factory = AppFeatureViewModel.Factory)
						)
					}
				}
			}
		}
	}
}
