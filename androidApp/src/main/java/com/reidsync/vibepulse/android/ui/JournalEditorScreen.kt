package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reidsync.vibepulse.android.ui.common.BaseToolbar
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.android.viewModel.JournalEditorViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewModel
import com.reidsync.vibepulse.model.Journal

/**
 * Created by Reid on 2023/12/18.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Composable
fun JournalEditorScreen(
	viewModel: JournalEditorViewModel,
	onNavigateUp: () -> Unit
) {
	val uiState by viewModel.uiState.collectAsState()
	Column(
		modifier = Modifier
	) {
		EditorToolbar(
			onNavigateUp = onNavigateUp,
			journal = uiState.journal
		)
	}
}

@Composable
fun EditorToolbar(
	onNavigateUp: () -> Unit,
	journal: Journal
) {
	BaseToolbar(
		modifier = Modifier
			.fillMaxWidth()
			.padding(15.dp)
			.height(40.dp),
		title = {
			Row(
				modifier = it
			) {
				Icon(
					Icons.Filled.Create,
					contentDescription = "Weather",
					modifier = Modifier
						.size(10.dp)
						.align(Alignment.Bottom),
					tint = Color.Green
				)
				Spacer(modifier = Modifier.width(5.dp))
				Text(
					text = journal.title,
					fontWeight = FontWeight.Bold,
					fontSize = 25.sp,
					color = Color.DarkGray
				)
			}

		},
		start = {
			Icon(
				Icons.Filled.KeyboardArrowLeft,
				contentDescription = "Back",
				modifier = it
					.size(10.dp)
					.clickable {
						onNavigateUp()
					},
				tint = Color.Green
			)

		},
		end = {
			Icon(
				Icons.Filled.Settings,
				contentDescription = "Back",
				modifier = it
					.size(10.dp)
					.clickable {
						onNavigateUp()
					},
				tint = Color.Green
			)
		}
	)
}