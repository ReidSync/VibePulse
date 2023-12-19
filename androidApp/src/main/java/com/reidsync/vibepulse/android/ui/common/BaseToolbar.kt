package com.reidsync.vibepulse.android.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created by Reid on 2023/12/18.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Composable
fun BaseToolbar(
	modifier: Modifier = Modifier,
	title: @Composable (modifier: Modifier) -> Unit,
	start: @Composable (modifier: Modifier) -> Unit = {},
	end: @Composable (modifier: Modifier) -> Unit = {}
) {
	Box(
		modifier = modifier
	) {
		start(Modifier.align(Alignment.CenterStart))
		title(Modifier.align(Alignment.Center))
		end(Modifier.align(Alignment.CenterEnd))
	}
}