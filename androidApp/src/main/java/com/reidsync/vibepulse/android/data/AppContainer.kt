package com.reidsync.vibepulse.android.data

import androidx.lifecycle.ViewModelProvider
import com.reidsync.vibepulse.android.APP
import com.reidsync.vibepulse.android.VibePulseApplication
import com.reidsync.vibepulse.android.data.impl.LocalNotebookRepository

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

interface AppContainer {
	val notebookRepository: NotebookRepository
}

class DefaultAppContainer : AppContainer {
	override val notebookRepository: NotebookRepository by lazy {
		LocalNotebookRepository(
			APP.applicationContext.filesDir
				.resolve("projects")
				.also { it.mkdirs() }
		)
	}
}