package com.reidsync.vibepulse.android.data

import com.reidsync.vibepulse.model.Journal
import com.reidsync.vibepulse.model.Notebook
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Reid on 2023/12/14.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

interface NotebookRepository {
	val journals: StateFlow<Notebook>
	suspend fun save(data: Notebook): Result<Unit>
	suspend fun load(): Result<Notebook>
	suspend fun add(item: Journal): Result<Unit>
	suspend fun update(item: Journal): Result<Unit>
}