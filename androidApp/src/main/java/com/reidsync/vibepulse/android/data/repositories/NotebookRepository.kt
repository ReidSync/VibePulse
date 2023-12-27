package com.reidsync.vibepulse.android.data.repositories

import com.reidsync.vibepulse.notebook.journal.Journal
import com.reidsync.vibepulse.notebook.journal.Notebook
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Reid on 2023/12/14.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

interface NotebookRepository {
	val notebook: StateFlow<Notebook>
	suspend fun save(data: Notebook): Result<Unit>
	suspend fun load(): Result<Notebook>
	suspend fun add(item: Journal): Result<Unit>
	suspend fun delete(item: Journal): Result<Unit>
	suspend fun update(item: Journal): Result<Unit>
}