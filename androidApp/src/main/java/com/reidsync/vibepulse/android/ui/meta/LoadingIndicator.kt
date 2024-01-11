package com.reidsync.vibepulse.android.ui.meta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Created by Reid on 2024/01/10.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

@Composable
fun LoadingIndicator(
	size: Dp
) {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator(modifier = Modifier.size(size))
	}
}