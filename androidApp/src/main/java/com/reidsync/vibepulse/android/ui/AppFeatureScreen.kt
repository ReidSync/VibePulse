package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import com.reidsync.vibepulse.android.ui.common.rememberMyBottomSheetNavigator
import com.reidsync.vibepulse.android.viewModel.AppFeatureViewModel
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.android.viewModel.JournalEditorViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewType
import com.reidsync.vibepulse.notebook.journal.Journal

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AppFeatureScreen(
	viewModel: AppFeatureViewModel
) {
	// https://google.github.io/accompanist/navigation-material/
	val bottomSheetNavigator = rememberMyBottomSheetNavigator(skipHalfExpanded = true)
	val navController = rememberNavController(bottomSheetNavigator)
	ModalBottomSheetLayout(
		bottomSheetNavigator,
		//modifier = Modifier,
		sheetShape = RoundedCornerShape(16.dp),
		sheetBackgroundColor = Color(0xFFF2F2F7),
		sheetElevation = 100.dp,

	) {
		NavHost(navController = navController, startDestination = Destination.HomeScreen.route) {
			composable(Destination.HomeScreen.route) {
				HomeScreen(
					viewModel = viewModel(factory = HomeViewModel.Factory),
					onCreateNewJournal = {
						viewModel.journal = it
						viewModel.metaViewType = JournalMetaViewType.Add
						navController.navigate(Destination.JournalMetaScreen.route)
					},
					onEditJournal = {
						viewModel.journal = it
						navController.navigate(Destination.JournalEditorScreen.route)
					}
				)
			}
			bottomSheet(Destination.JournalMetaScreen.route) {
				JournalMetaScreen(
					viewModel = viewModel(
						factory = JournalMetaViewModel
							.Factory(viewModel.journal, viewModel.metaViewType)
					),
					onNavigateUp = {
						navController.navigateUp()
					}
				)
			}
			composable(Destination.JournalEditorScreen.route) {
				JournalEditorScreen(
					viewModel = viewModel(factory = JournalEditorViewModel.Factory(viewModel.journal)),
					onNavigateUp = {
						navController.navigateUp()
					},
					onNavigateMetaEdit = {
						viewModel.journal = it
						viewModel.metaViewType = JournalMetaViewType.Edit
						navController.navigate(Destination.JournalMetaScreen.route)
					}
				)
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
		AppFeatureScreen(
			viewModel(
				factory = AppFeatureViewModel.Factory
			)
		)
	}
}