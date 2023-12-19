package com.reidsync.vibepulse.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reidsync.vibepulse.android.R
import com.reidsync.vibepulse.android.viewModel.HomeViewModel
import com.reidsync.vibepulse.model.Journal
import com.reidsync.vibepulse.util.format

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Composable
fun HomeScreen(
	viewModel: HomeViewModel,
	onCreateNewJournal: (Journal) -> Unit,
	onEditJournal: (Journal) -> Unit
) {
	val uiState by viewModel.uiState.collectAsState()
	Column(
		modifier = Modifier
			.padding(10.dp)
	) {
		Spacer(Modifier.height(20.dp))
		Text(
			text = stringResource(R.string.app_name),
			fontWeight = FontWeight(800),
			fontSize = 35.sp,
			color = Color.Black
		)
		Spacer(Modifier.height(5.dp))
		Box(
			modifier = Modifier
				.padding(5.dp)
		) {
			JournalList(
				journals = uiState.journals,
				onEditJournal = onEditJournal
			)
			CreateButton(
				Modifier
					.align(Alignment.BottomCenter)
					.padding(bottom = 10.dp)
			) {
				onCreateNewJournal(Journal())
			}
		}
	}
}

@Composable
fun JournalList(
	modifier: Modifier = Modifier,
	journals: List<Journal>,
	onEditJournal: (Journal) -> Unit
) {
	val listState = rememberLazyListState()
	Box(modifier = modifier
		.pointerInput(Unit) {
			detectTapGestures(onTap = {
				//focusManager.clearFocus()
			})
		}
		.fillMaxSize()
	) {
		LazyColumn(
			state = listState,
			modifier = Modifier
				.fillMaxSize()
		) {
			items(journals) {
				JournalListItem(
					modifier = Modifier
						.height(70.dp)
						.padding(bottom = 3.dp)
						.clickable {
							onEditJournal(it)
						},
					journal = it
				)
			}
		}
	}
}

@Composable
fun JournalListItem(
	modifier: Modifier = Modifier,
	journal: Journal
) {
	Box(
		modifier = modifier
			.clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
			.fillMaxWidth()
			.background(Color.White)
			.padding(10.dp)
	) {
		Row {
			Column(
				modifier = Modifier
					.fillMaxHeight(),
				horizontalAlignment = Alignment.Start,
				verticalArrangement = Arrangement.Center
			) {
				Text(
					text = journal.titleWithPlaceHolder,
					fontWeight = FontWeight.Bold,
					fontSize = 18.sp,
					color = Color.Black,
					modifier = Modifier
						.padding(bottom = 10.dp)
				)
				Row(
					//verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						Icons.Filled.DateRange,
						contentDescription = "Calendar",
						modifier = Modifier
							.size(10.dp)
							.align(Alignment.Bottom)
					)

					Spacer(modifier = Modifier.width(5.dp))

					Text(
						text = journal.date.format("EE, MMM d, yyyy   h:mm:ss a"),
						fontWeight = FontWeight.Light,
						fontSize = 10.sp,
						color = Color.Black
					)
				}
			}


			Box(
				modifier = Modifier
					.fillMaxSize(),
				contentAlignment = Alignment.CenterEnd,
			) {
				Icon(
					Icons.Default.KeyboardArrowRight,
					contentDescription = "enter",
					modifier = Modifier
						//.align(alignment = Alignment.CenterHorizontally)
						.size(20.dp),
					tint = Color.LightGray
				)

			}


		}
	}
}

@Composable
fun CreateButton(
	modifier: Modifier,
	onCreate: () -> Unit
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Button(
			onClick = {
				onCreate()
			},
			colors = ButtonDefaults.buttonColors(Color.Blue),
			shape = CircleShape,
			modifier = Modifier
				.size(70.dp)
				.shadow(10.dp, CircleShape),
		) {
			Icon(
				Icons.Filled.Add,
				contentDescription = "Create",
				modifier = Modifier
					.size(100.dp)
				//.background(Color.Red)
			)
		}
	}
}

@Composable
fun NewJournalSheetToolbar(
	onNavigateUp: () -> Unit,
	addNewJournal: (item: Journal) -> Unit
) {

}


//@Preview
//@Composable
//fun HomeScreenPreview() {
//	MyApplicationTheme {
//		Surface(
//			modifier = Modifier.fillMaxSize(),
//			color = Color(0xFFF2F2F7)
//		) {
//			HomeScreen(
//				viewModel(factory = HomeViewModel.Factory),
//				onCreateNewJournal = {}
//			)
//		}
//	}
//}