package com.reidsync.vibepulse.android.ui.meta

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reidsync.vibepulse.android.AppThemeColor
import com.reidsync.vibepulse.android.data.FeelingEmojis
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
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			color = AppThemeColor.current.vibePulseColors.vibeA.toColor(),
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
		title = "Give a title for today",
		content = {
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth(),
				value = journal.title,
				onValueChange = {
					update(it)
				},
				colors = OutlinedTextFieldDefaults.colors(
					focusedBorderColor = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
					unfocusedBorderColor = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
					focusedContainerColor = AppThemeColor.current.vibePulseColors.background.toColor(),
					unfocusedContainerColor = AppThemeColor.current.vibePulseColors.background.toColor(),
					unfocusedTextColor = AppThemeColor.current.vibePulseColors.vibeC.toColor(),
					focusedTextColor = AppThemeColor.current.vibePulseColors.vibeC.toColor(),
					//cursorColor = Color.Black
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
			val listState = rememberLazyListState()
			val matrix = ColorMatrix()
			matrix.setToSaturation(0F)

			LazyRow(
				state = listState,
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
				//verticalAlignment = Alignment.CenterVertically,
				contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp)
			) {
				items(feelings) { feeling ->
					FeelingEmojis[feeling]?.let { emoji ->
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
								.size(70.dp)
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
	update: (MoodFactors, Boolean) -> Unit
) {
	JournalInfoField(
		title = "What's affecting your mood?",
		content = {
			LazyVerticalStaggeredGrid(
				columns = StaggeredGridCells.Adaptive(100.dp),
				verticalItemSpacing = 12.dp,
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				this.items(moodFactors) { moodFactor ->
					MoodFactorTextButton(
						moodFactor = moodFactor,
						isSelected = journal.moodFactors.contains(moodFactor),
						onClicked = update
					)
				}
			}
		}
	)
}

@Composable
fun MoodFactorTextButton(
	moodFactor: MoodFactors,
	isSelected: Boolean,
	onClicked: (MoodFactors, Boolean) -> Unit
) {
	val radius = 20.dp
	val backgroundColor = if (isSelected) {
		AppThemeColor.current.vibePulseColors.vibeA.toColor()
	} else {
		AppThemeColor.current.vibePulseColors.background.toColor()
	}

	Box(
		modifier = Modifier
			.clip(RoundedCornerShape(radius))
			.background(
				color = backgroundColor,
				shape = RoundedCornerShape(radius)
			)
			.border(
				2.dp,
				AppThemeColor.current.vibePulseColors.vibeD.toColor(),
				shape = RoundedCornerShape(radius)
			)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = rememberRipple(
					bounded = true,
					color = backgroundColor
				),
				onClick = {
					onClicked(moodFactor, !isSelected)
				}
			)
			.padding(top = 10.dp, bottom = 10.dp)
	) {
		Text(
			text = moodFactor.displayName,
			fontSize = 16.sp,
			color = AppThemeColor.current.vibePulseColors.vibeD.toColor(),
			modifier = Modifier
				.align(Alignment.Center)
				.padding(4.dp),

		)
	}
}