package com.reidsync.vibepulse.android.ui.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

/**
 * Created by Reid on 2023/12/26.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@ExperimentalMaterialNavigationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberMyBottomSheetNavigator(
	animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
	skipHalfExpanded: Boolean = false,
): BottomSheetNavigator {
	val sheetState = rememberModalBottomSheetState(
		ModalBottomSheetValue.Hidden,
		animationSpec = animationSpec,
		skipHalfExpanded = skipHalfExpanded
	)
	return remember { BottomSheetNavigator(sheetState) }
}