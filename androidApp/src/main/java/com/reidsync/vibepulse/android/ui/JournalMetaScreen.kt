package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reidsync.vibepulse.android.MyApplicationTheme
import com.reidsync.vibepulse.android.ui.common.BaseToolbar
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
	onNavigateUp: () -> Unit,
) {
	val uiState by viewModel.uiState.collectAsState()
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		JournalMetaToolbar(
			onNavigateUp,
			viewModel
		)
		JournalMetaContents(
			journal = uiState.journal,
			onValueChange = {
				viewModel.updateJournal(it)
			}
		)

	}
}

@Composable
fun JournalMetaContents(
	journal: Journal,
	onValueChange: (Journal) -> Unit,
) {

	val focusManager = LocalFocusManager.current
	val doneWithClearingFocus: () -> Unit = {
		onValueChange(journal)
		focusManager.clearFocus()
	}

	@Composable
	fun JournalInfoField(
		title: String,
		content: @Composable () -> Unit
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(5.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Start
		) {
			Text(
				text = title,
				fontSize = 15.sp,
				color = Color.Black,
			)
			Spacer(Modifier.width(5.dp))
			content()
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(Unit) {
				detectTapGestures(onTap = {
					doneWithClearingFocus()
				})
			}
			.padding(20.dp)
	) {
		Text(
			text = "JOURNAL INFO",
			fontSize = 13.sp,
			color = Color.Gray,
			modifier = Modifier
				.padding(start = 10.dp, bottom = 10.dp)
		)

		JournalInfoField(
			title = "Title",
			content = {
				OutlinedTextField(
					modifier = Modifier
						.fillMaxWidth(),
					value = journal.title,
					onValueChange = {
						onValueChange(journal.copy(title = it))
					},
					colors = TextFieldDefaults.colors(
						focusedContainerColor = Color.White,
						unfocusedContainerColor = Color.White,
						unfocusedTextColor = Color.LightGray,
						focusedTextColor = Color.Black,
						focusedIndicatorColor = Color.White,
						unfocusedIndicatorColor = Color.White,
						cursorColor = Color.Black
					),
					placeholder = {
						Text(
							text = journal.title.ifEmpty { "Title" },
							color = Color.LightGray
						)
					},
					maxLines = 1,
					keyboardOptions = KeyboardOptions.Default.copy(
						imeAction = ImeAction.Done,
						keyboardType = KeyboardType.Text
					),
					keyboardActions = KeyboardActions(
						onDone = {
							doneWithClearingFocus()
						}
					)
				)
			}
		)
	}
}

@Composable
fun JournalMetaToolbar(
	onNavigateUp: () -> Unit,
	viewModel: JournalMetaViewModel
) {
	BaseToolbar(
		modifier = Modifier
			.fillMaxWidth()
			.padding(15.dp)
			.height(40.dp),
		title = {
			Text(
				text = viewModel.title,
				fontWeight = FontWeight.Bold,
				fontSize = 18.sp,
				color = Color.Black,
				modifier = it
			)
		},
		start = {
			Text(
				text = viewModel.dismiss,
				color = Color.Blue,
				fontSize = 16.sp,
				modifier = it
					.clickable {
						onNavigateUp()
					}
			)
		},
		end = {
			Text(
				text = viewModel.submit,
				color = Color.Blue,
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp,
				modifier = it
					.clickable {
						viewModel.submitAction()
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
				viewModel(
					factory = JournalMetaViewModel.Factory(
						Journal(),
						JournalMetaViewType.Add
					)
				),
				onNavigateUp = {}
			)
		}
	}
}