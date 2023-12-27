package com.reidsync.vibepulse.android.di

import com.reidsync.vibepulse.android.APP
import com.reidsync.vibepulse.android.data.repositories.impl.LocalNotebookRepository
import com.reidsync.vibepulse.android.data.repositories.NotebookRepository

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