package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.reidsync.vibepulse.android.MyApplicationTheme
import com.reidsync.vibepulse.android.data.HOME
import com.reidsync.vibepulse.android.data.JOURNAL_EDITOR
import com.reidsync.vibepulse.android.data.JOURNAL_META
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewModel
import com.reidsync.vibepulse.model.Journal

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AppFeatureScreen() {
	// https://google.github.io/accompanist/navigation-material/
	val bottomSheetNavigator = rememberBottomSheetNavigator()
	val navController = rememberNavController(bottomSheetNavigator)
	ModalBottomSheetLayout(
		bottomSheetNavigator,
		modifier = Modifier
			.height(500.dp),
		sheetShape = RoundedCornerShape(16.dp),
		sheetBackgroundColor = Color(0xFFF2F2F7),
		sheetElevation = 100.dp,
	) {
		var journalMetaDoneAction: (Journal)-> Unit = {}
		NavHost(navController = navController, startDestination = Destination.HomeScreen.route) {
			composable(Destination.HomeScreen.route) {
				HomeScreen(
					viewModel = viewModel(factory = HomeViewModel.Factory),
					onCreateNewJournal = {
						navController.navigate(Destination.JournalMetaScreen.route)
						journalMetaDoneAction = it
					}
				)
			}
			bottomSheet(Destination.JournalMetaScreen.route) {
				//if (it.destination.route == Destination.HomeScreen.route) { }
				JournalMetaScreen(
					viewModel = JournalMetaViewModel(Journal()),
					toolbar = {
						SimpleToolbar(
							modifier = Modifier
								.fillMaxWidth()
								.height(40.dp),
							title = {
								Text(
									text = "New Journal",
									modifier = it
								)
							},
							start = {
								Text(
									text = "Dismiss",
									modifier = it
										.clickable {
											navController.navigateUp()
										}
								)
							},
							end = {
								Text(
									text = "Done",
									modifier = it
										.clickable {
											journalMetaDoneAction(Journal())
											navController.navigateUp()
										}
								)
							}

						)
					}

				)
			}
			composable(Destination.JournalEditorScreen.route) {
			}
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
fun AppFeatureScreenPreview() {
	MyApplicationTheme {
		AppFeatureScreen()
	}
}