package com.reidsync.vibepulse.android.data.impl

import com.reidsync.vibepulse.android.data.NotebookRepository
import com.reidsync.vibepulse.model.Notebook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File

/**
 * Created by Reid on 2023/12/14.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class LocalNotebookRepository constructor(private val projectsRoot: File): NotebookRepository {
	@OptIn(ExperimentalCoroutinesApi::class)
	private val saveDispatcher = Dispatchers.IO.limitedParallelism(1)
	private val _journals = MutableStateFlow(Notebook())
	override val journals: StateFlow<Notebook> = _journals.asStateFlow()

	override suspend fun save(data: Notebook): Result<Unit> =
		withContext(saveDispatcher) {
			runCatching {
				_journals.update {
					it.copy(journals = data.journals)
				}
				projectsRoot
					.resolve("my-journals.json")
					.writeText(Json.encodeToString(Json.serializersModule.serializer(), data))

			}
		}

	override suspend fun load(): Result<Notebook> {
		val file = projectsRoot.resolve("my-journals.json")
		return runCatching {
			val content = file.readText()
			_journals.update {
				val data = Json.decodeFromString<Notebook>(content)
				it.copy(journals = data.journals)
			}
			journals.value
		}
	}
}