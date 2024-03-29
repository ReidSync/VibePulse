package com.reidsync.vibepulse.notebook.journal

import com.reidsync.vibepulse.network.weather.WeatherCodeInfo
import kotlinx.datetime.toLocalDateTime
import com.reidsync.vibepulse.primitives.uuid.UUID
import com.reidsync.vibepulse.primitives.uuid.randomUUID
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

@Serializable
data class Journal(
	val id: UUID = UUID.randomUUID(),
	val date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
	val title: String = "",
	val feeling: Feelings = Feelings.Neutral,
	val moodFactors: Set<MoodFactors> = emptySet(),
	val location: JournalLocation = JournalLocation(),
	val weather: JournalWeather = JournalWeather(),
	val contents: String = ""
) {
	val titleWithPlaceHolder = title.ifEmpty { "New Journal" }
	val contentsWithPlaceHolder = contents.ifEmpty { "Start writing..." }

	companion object {
		fun makeInstance(): Journal = Journal()
	}

}

val Journal.Companion.mock: Journal
	get() = Journal(
		id = UUID.randomUUID(),
		title = "Test title",
		date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
		contents = """
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor \
          incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud \
          exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure \
          dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. \
          Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt \
          mollit anim id est laborum.
          """
	)