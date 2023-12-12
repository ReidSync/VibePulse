package com.reidsync.vibeforge.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reidsync.vibeforge.android.MyApplicationTheme

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Composable
fun HomeScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize()
	) {
		CreateButton(Modifier.padding(bottom = 20.dp))
	}
}

@Composable
fun CreateButton(modifier: Modifier) {
	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Bottom
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
			color = MaterialTheme.colorScheme.background
		) {
			HomeScreen()
		}
	}
}
