package com.reidsync.vibepulse.android.ui.appfeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.android.VibePulseApplication
import com.reidsync.vibepulse.android.data.repositories.NotebookRepository
import com.reidsync.vibepulse.notebook.journal.Journal
import com.reidsync.vibepulse.notebook.journal.JournalMetaViewType

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class AppFeatureViewModel(
	private val notebookRepository: NotebookRepository
) : ViewModel() {

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as VibePulseApplication)
				AppFeatureViewModel(
					notebookRepository = application.container.notebookRepository
				)
			}
		}
	}

	var journal: Journal = Journal()
	var metaViewType: JournalMetaViewType = JournalMetaViewType.Add

}