package com.reidsync.vibepulse.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.android.VibePulseApplication
import com.reidsync.vibepulse.android.data.NotebookRepository
import com.reidsync.vibepulse.notebook.journal.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class JournalMetaViewModel(
	val journal: Journal,
	val type: JournalMetaViewType,
	private val notebookRepository: NotebookRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(JournalMetaUIState())
	val uiState: StateFlow<JournalMetaUIState> = _uiState.asStateFlow()
	val dismiss: String = "Dismiss"
	val submit: String
		get() = when(type) {
			is JournalMetaViewType.Add -> "Add"
			is JournalMetaViewType.Edit -> "Done"
		}
	val title: String
		get() = when(type) {
			is JournalMetaViewType.Add -> "New Journal"
			is JournalMetaViewType.Edit -> "Edit"
		}

	companion object {
		fun Factory(
			journal: Journal,
			type: JournalMetaViewType
		): ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as VibePulseApplication)
				JournalMetaViewModel(
					journal = journal,
					type = type,
					notebookRepository = application.container.notebookRepository)
			}
		}
	}

	init {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

	fun submitAction() {
		when (type) {
			is JournalMetaViewType.Add -> {
				viewModelScope.launch {
					notebookRepository.add(uiState.value.journal)
				}
			}
			is JournalMetaViewType.Edit -> {
				viewModelScope.launch {
					notebookRepository.update(uiState.value.journal)
				}
			}
		}
	}

	fun updateScreen(journal: Journal) {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

	fun updateTitle(title: String) {
		updateScreen(_uiState.value.journal.copy(title = title))
	}

}

data class JournalMetaUIState(
	val journal: Journal = Journal()
) {
	val title = journal.title
}

sealed class JournalMetaViewType {
	data object Add: JournalMetaViewType()
	data object Edit: JournalMetaViewType()

}