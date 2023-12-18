package com.reidsync.vibepulse.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.reidsync.vibepulse.android.ui.AppFeatureScreen

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApplicationTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = Color(0xFFF2F2F7)
				) {
					AppFeatureScreen()
				}
			}
		}
	}
}
