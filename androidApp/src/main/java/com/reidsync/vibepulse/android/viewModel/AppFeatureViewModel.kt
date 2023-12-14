package com.reidsync.vibepulse.android.viewModel

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class AppFeatureViewModel(
	private val notebookRepository: NotebookRepository
): ViewModel() {

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

}