package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reidsync.vibepulse.android.MyApplicationTheme
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewType
import com.reidsync.vibepulse.model.Journal

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


@Composable
fun JournalMetaScreen(
	viewModel: JournalMetaViewModel,
	onNavigateUp: ()->Unit,
) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		JournalMetaToolbar(
			onNavigateUp,
			viewModel
		)
		Text("Title")
	}
}

@Composable
fun JournalMetaToolbar(
	onNavigateUp: ()->Unit,
	viewModel: JournalMetaViewModel
) {
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
						onNavigateUp()
					}
			)
		},
		end = {
			Text(
				text = "Done",
				modifier = it
					.clickable {
						viewModel.endAction()
						onNavigateUp()
					}
			)
		}
	)
}

@Preview
@Composable
fun JournalMetaScreenPreview() {
	MyApplicationTheme {
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = Color(0xFFF2F2F7)
		) {
			JournalMetaScreen(
				viewModel(factory = JournalMetaViewModel.Factory(Journal(), JournalMetaViewType.Add)),
				onNavigateUp = {}
			)
		}
	}
}