package com.reidsync.vibepulse.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.android.VibePulseApplication
import com.reidsync.vibepulse.android.data.NotebookRepository
import com.reidsync.vibepulse.model.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Reid on 2023/12/18.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class JournalEditorViewModel(
	val journal: Journal,
	private val notebookRepository: NotebookRepository
): ViewModel() {
	private val _uiState = MutableStateFlow(JournalEditorUIState())
	val uiState: StateFlow<JournalEditorUIState> = _uiState.asStateFlow()

	companion object {
		fun Factory(
			journal: Journal
		): ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as VibePulseApplication)
				JournalEditorViewModel(
					journal = journal,
					notebookRepository = application.container.notebookRepository)
			}
		}
	}

	init {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

	fun updateJournal(journal: Journal) {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

}

data class JournalEditorUIState(
	val journal: Journal = Journal()
) {
	val title = journal.title
}