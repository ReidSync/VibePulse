package com.reidsync.vibepulse.android.data.impl

import com.reidsync.vibepulse.android.data.NotebookRepository
import com.reidsync.vibepulse.model.Journal
import com.reidsync.vibepulse.model.Notebook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
	private val _notebook = MutableStateFlow(Notebook())
	override val notebook: StateFlow<Notebook> = _notebook.asStateFlow()

	init {
		CoroutineScope(Dispatchers.Default).launch {
			load()
		}
	}

	override suspend fun save(data: Notebook): Result<Unit> =
		withContext(saveDispatcher) {
			runCatching {
				_notebook.update {
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
			_notebook.update {
				val data = Json.decodeFromString<Notebook>(content)
				it.copy(journals = data.journals)
			}
			notebook.value
		}
	}


	override suspend fun add(item: Journal): Result<Unit> {
		val newNotebook = notebook.value.copy (
			journals = notebook.value.journals + item
		)
		return save(newNotebook)
	}

	override suspend fun update(item: Journal): Result<Unit> {
		return runCatching {
			val index = notebook.value.journals.indexOfFirst { it.id == item.id }
			val newJournals = notebook.value.journals.toMutableList()
			newJournals[index] = item
			_notebook.update {
				it.copy(journals = newJournals)
			}
		}
	}

}