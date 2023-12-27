package com.reidsync.vibepulse.android.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.reidsync.vibepulse.android.data.conventions.toColor
import com.reidsync.vibepulse.primitives.colors.SolidColor

/**
 * Created by Reid on 2023/12/28.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableItem(
	modifier: Modifier,
	onDelete:()-> Unit,
	content: @Composable () -> Unit
) {
	val dismissState = rememberDismissState(
		initialValue = DismissValue.Default,
		confirmStateChange = { dismissValue ->
			if (dismissValue == DismissValue.DismissedToStart ||
				dismissValue == DismissValue.DismissedToEnd) {
				onDelete()
				true
			} else {
				false
			}
		}
	)

	if (dismissState.isDismissed(DismissDirection.EndToStart) ||
		dismissState.isDismissed(DismissDirection.StartToEnd)
	) {
		onDelete()
	}

	SwipeToDismiss(
		state = dismissState,
		modifier = modifier,
		directions = setOf(
			//DismissDirection.StartToEnd,
			DismissDirection.EndToStart
		),
		dismissThresholds = { direction ->
			FractionalThreshold(
				if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
			)
		},
		background = {
			SwipeBackground(dismissState)
		},
		dismissContent = {
			content()
		}
	)
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SwipeBackground(dismissState: DismissState) {
	val direction = dismissState.dismissDirection ?: return
	val color by animateColorAsState(
		when (dismissState.targetValue) {
			DismissValue.Default -> SolidColor.LightGray.toColor()
			DismissValue.DismissedToEnd -> SolidColor.Green.toColor()
			DismissValue.DismissedToStart -> SolidColor.LightRed.toColor()
		}, label = ""
	)
	val alignment = when (direction) {
		DismissDirection.StartToEnd -> Alignment.CenterStart
		DismissDirection.EndToStart -> Alignment.CenterEnd
	}
	val icon = when (direction) {
		DismissDirection.StartToEnd -> Icons.Default.Done
		DismissDirection.EndToStart -> Icons.Default.Delete
	}
	val scale by animateFloatAsState(
		if (dismissState.targetValue == DismissValue.Default) 0.75f else 1.2f, label = ""
	)

	Box(
		Modifier
			.fillMaxSize()
			.background(color)
			.padding(horizontal = 20.dp),
		contentAlignment = alignment
	) {
		Icon(
			icon,
			tint = SolidColor.MidWhite.toColor(),
			contentDescription = "Localized description",
			modifier = Modifier.scale(scale),
		)
	}
}
