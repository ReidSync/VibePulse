package com.reidsync.vibepulse.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.model.Journal
import com.reidsync.vibepulse.model.mock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class HomeScreenViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(HomeScreenUIState())
	val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				HomeScreenViewModel()
			}
		}
	}
}
data class HomeScreenUIState(
	//val journals: List<Journal> = emptyList()
	val journals: List<Journal> = listOf(
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock,
		Journal.mock, Journal.mock, Journal.mock
	)
)