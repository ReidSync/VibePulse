package com.reidsync.vibepulse.android.ui.appfeature

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.reidsync.vibepulse.android.data.HOME
import com.reidsync.vibepulse.android.data.JOURNAL_EDITOR
import com.reidsync.vibepulse.android.data.JOURNAL_META
import com.reidsync.vibepulse.android.ui.common.rememberMyBottomSheetNavigator
import com.reidsync.vibepulse.android.ui.editor.JournalEditorScreen
import com.reidsync.vibepulse.android.ui.editor.JournalEditorViewModel
import com.reidsync.vibepulse.android.ui.home.HomeScreen
import com.reidsync.vibepulse.android.ui.home.HomeViewModel
import com.reidsync.vibepulse.android.ui.meta.JournalMetaScreen
import com.reidsync.vibepulse.android.ui.meta.JournalMetaViewModel
import com.reidsync.vibepulse.android.ui.meta.JournalMetaViewType
import com.reidsync.vibepulse.android.util.MyApplicationTheme

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
	val bottomSheetNavigator = rememberMyBottomSheetNavigator(
		animationSpec = SpringSpec<Float>(
			dampingRatio = Spring.DampingRatioNoBouncy,
			stiffness = Spring.StiffnessLow
		),
		skipHalfExpanded = true
	)

	val navController = rememberNavController(bottomSheetNavigator)
	ModalBottomSheetLayout(
		bottomSheetNavigator,
		//modifier = Modifier,
		sheetShape = RoundedCornerShape(16.dp),
		sheetBackgroundColor = Color(0xFFF2F2F7),
		sheetElevation = 30.dp,

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
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.fillMaxSize(0.98f)
				) {
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

			}
			composable(
				Destination.JournalEditorScreen.route,
				enterTransition = {
					when (initialState.destination.route) {
						Destination.HomeScreen.route ->
							slideIntoContainer(
								AnimatedContentTransitionScope.SlideDirection.Left,
								animationSpec = tween(400)
							)

						else -> null
					}
				},
				exitTransition = {
					when (targetState.destination.route) {
						Destination.HomeScreen.route ->
							slideOutOfContainer(
								AnimatedContentTransitionScope.SlideDirection.Left,
								animationSpec = tween(400)
							)

						else -> null
					}
				},
				popEnterTransition = {
					when (initialState.destination.route) {
						Destination.HomeScreen.route ->
							slideIntoContainer(
								AnimatedContentTransitionScope.SlideDirection.Right,
								animationSpec = tween(400)
							)

						else -> null
					}
				},
				popExitTransition = {
					when (targetState.destination.route) {
						Destination.HomeScreen.route ->
							slideOutOfContainer(
								AnimatedContentTransitionScope.SlideDirection.Right,
								animationSpec = tween(400)
							)

						else -> null
					}
				}
			) {
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