package com.reidsync.vibepulse.android.ui.meta

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reidsync.vibepulse.android.AppThemeColor
import com.reidsync.vibepulse.android.R
import com.reidsync.vibepulse.android.data.conventions.toColor
import com.reidsync.vibepulse.notebook.journal.Feelings
import com.reidsync.vibepulse.notebook.journal.Journal
import com.reidsync.vibepulse.notebook.journal.MoodFactors


/**
 * Created by Reid on 2023/12/26.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Composable
fun JournalInfoField(
	title: String,
	content: @Composable () -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(5.dp)
	) {
		Text(
			text = title,
			fontSize = 16.sp,
			color = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
		)
		Spacer(Modifier.height(5.dp))
		content()
	}
}


@Composable
fun TitleField(
	journal: Journal,
	update: (String) -> Unit,
	clearFocus: () -> Unit
) {
	JournalInfoField(
		title = "Give a title for your today",
		content = {
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth(),
				value = journal.title,
				onValueChange = {
					update(it)
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
						text = journal.titleWithPlaceHolder,
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
						clearFocus()
					}
				)
			)
		}
	)
}

@Composable
fun FeelingsField(
	feelings: Array<Feelings>,
	journal: Journal,
	update: (Feelings) -> Unit,
	clearFocus: () -> Unit
) {
	JournalInfoField(
		title = "How are you feeling?",
		content = {
			val emojis = mapOf(
				Feelings.Sad to R.drawable.sad_enabled,
				Feelings.Angry to R.drawable.angry_enabled,
				Feelings.Neutral to R.drawable.neutral_enabled,
				Feelings.Happy to R.drawable.happy_enabled,
				Feelings.SuperHappy to R.drawable.super_happy_enabled
			)

			val listState = rememberLazyListState()
			val matrix = ColorMatrix()
			matrix.setToSaturation(0F)

			LazyRow(
				state = listState,
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
				contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
			) {
				items(feelings) { feeling ->
					emojis[feeling]?.let { emoji ->
						val isSelected = journal.feeling == feeling
						val scale by animateFloatAsState(
							targetValue = if (isSelected) 1.4f else 1f,
							label = ""
						)
						val color by animateColorAsState(
							targetValue = if (isSelected) AppThemeColor.current.vibePulseColors.vibeA.toColor() else Color.Transparent,
							label = ""
						)

						Image(
							painter = painterResource(id = emoji),
							contentDescription = null,
							colorFilter = if (isSelected) null else
								ColorFilter.colorMatrix(matrix),
							modifier = Modifier
								.size(54.dp)
								.padding(8.dp)
								.clip(CircleShape)
								.clickable(
									interactionSource = remember { MutableInteractionSource() },
									indication = rememberRipple(
										bounded = false,
										color = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
										radius = 54.dp / 2
									),
									onClick = {
										clearFocus()
										update(feeling)
									}
								)
								.scale(scale)
								.background(color, CircleShape)
								.padding(4.dp)
						)
					}
				}
			}
		}
	)
}

@Composable
fun MoodFactorsField(
	moodFactors: Array<MoodFactors>,
	journal: Journal,
	update: (List<MoodFactors>) -> Unit,
	clearFocus: () -> Unit
) {
	MoodFactors.Work.name

}


