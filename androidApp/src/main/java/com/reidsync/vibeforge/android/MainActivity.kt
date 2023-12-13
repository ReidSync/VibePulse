package com.reidsync.vibeforge.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reidsync.vibeforge.android.model.HOME
import com.reidsync.vibeforge.android.model.JOURNAL_EDITOR
import com.reidsync.vibeforge.android.model.JOURNAL_META
import com.reidsync.vibeforge.android.ui.HomeScreen

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApplicationTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = Color(0xFFF2F2F7)
				) {
					AppScreen()
				}
			}
		}
	}
}

@Composable
fun AppScreen() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Destination.HomeScreen.route) {
		composable(Destination.HomeScreen.route) {
			HomeScreen()
		}
		composable(Destination.JournalMetaScreen.route) {
		}
		composable(Destination.JournalEditorScreen.route) {
		}
	}
}

sealed class Destination(
	val route: String
) {
	data object HomeScreen : Destination(
		route = HOME
	)

	data object JournalMetaScreen : Destination(
		route = JOURNAL_META
	)

	data object JournalEditorScreen : Destination(
		route = JOURNAL_EDITOR
	)
}

@Preview
@Composable
fun DefaultPreview() {
	MyApplicationTheme {
		AppScreen()
	}
}
