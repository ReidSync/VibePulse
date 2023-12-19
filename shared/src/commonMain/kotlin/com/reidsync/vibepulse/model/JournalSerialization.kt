package com.reidsync.vibepulse.model

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Created by Reid on 2023/12/19.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

private val prettyJson = Json { prettyPrint = true }

fun Notebook.serialize(): String {
	return prettyJson.encodeToString(Json.serializersModule.serializer(), this)
}

fun deserializeToNotebook(data: String) : Result<Notebook> {
	return runCatching {
		Json.decodeFromString<Notebook>(data)
	}.onFailure {
		throw JournalSerializationException(it.message)
	}
}

fun Journal.serialize(): String {
	return prettyJson.encodeToString(Json.serializersModule.serializer(), this)
}

fun deserializeToJournal(data: String) : Result<Journal> {
	return runCatching {
		Json.decodeFromString<Journal>(data)
	}.onFailure {
		throw JournalSerializationException(it.message)
	}
}

fun deserializeJournalOrNull(data: String) = deserializeToJournal(data).getOrNull()
fun deserializeNotebookOrNull(data: String) = deserializeToNotebook(data).getOrNull()

@Throws(JournalSerializationException::class)
fun deserializeJournalOrThrow(data: String) = deserializeToJournal(data).getOrThrow()

//@Throws(Exception::class)
@Throws(JournalSerializationException::class)
fun deserializeNotebookOrThrow(data: String) = deserializeToNotebook(data).getOrThrow()


class JournalSerializationException(message: String? = null) : Exception(message)