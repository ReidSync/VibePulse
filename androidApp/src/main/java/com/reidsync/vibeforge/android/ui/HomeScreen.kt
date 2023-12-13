package com.reidsync.vibeforge.android.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reidsync.vibeforge.android.MyApplicationTheme
import com.reidsync.vibeforge.android.R
import com.reidsync.vibeforge.model.Journal
import com.reidsync.vibeforge.model.mock

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Composable
fun HomeScreen() {
	val focusManager = LocalFocusManager.current
	val listState = rememberLazyListState()
	val journals: List<Journal> = listOf(
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock
	)

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
			Box(modifier = Modifier
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
								.padding(bottom = 3.dp),
							journal = it
						)
					}
				}
			}

			CreateButton(
				Modifier
					.align(Alignment.BottomCenter)
					.padding(bottom = 10.dp)
			)
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
					text = journal.title,
					fontWeight = FontWeight.Bold,
					fontSize = 18.sp,
					color = Color.Black,
					modifier = Modifier
						.padding(bottom = 10.dp)
				)
				Text(
					text = "${journal.date}",
					fontWeight = FontWeight.Light,
					fontSize = 11.sp,
					color = Color.Black
				)
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
						.size(20.dp)
				)

			}


		}
	}
}

@Composable
fun CreateButton(modifier: Modifier) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Button(
			onClick = {
				println("create button")
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


@Preview
@Composable
fun DefaultPreview() {
	MyApplicationTheme {
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = Color(0xFFF2F2F7)
		) {
			HomeScreen()
		}
	}
}