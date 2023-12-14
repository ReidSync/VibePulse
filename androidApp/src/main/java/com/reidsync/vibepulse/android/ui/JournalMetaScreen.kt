package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reidsync.vibepulse.android.MyApplicationTheme
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.android.viewModel.JournalMetaViewModel

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


@Composable
fun JournalMetaScreen(
	viewModel: JournalMetaViewModel,
	toolbar: @Composable () -> Unit = {}
) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		toolbar()
		Text("Title")
	}
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
				viewModel(factory = JournalMetaViewModel.Factory)
			)
		}
	}
}