package com.reidsync.vibepulse.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.model.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class JournalMetaViewModel(
	val journal: Journal,
	val type: JournalMetaViewType
) : ViewModel() {
	private val _uiState = MutableStateFlow(JournalMetaUIState())
	val uiState: StateFlow<JournalMetaUIState> = _uiState.asStateFlow()

	companion object {
//		val Factory: ViewModelProvider.Factory = viewModelFactory {
//			initializer {
//				JournalMetaViewModel(Journal())
//			}
//		}
		fun Factory(
			journal: Journal,
			type: JournalMetaViewType
		): ViewModelProvider.Factory = viewModelFactory {
			initializer {
				JournalMetaViewModel(journal, type)
			}
		}
	}
}

data class JournalMetaUIState(
	val journal: Journal = Journal()
)

sealed class JournalMetaViewType {
	data object Add: JournalMetaViewType()
	data object Edit: JournalMetaViewType()

}