package com.reidsync.vibepulse.notebook.journal

/**
 * Created by Reid on 2023/12/21.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


fun Journal.edit(actions: JournalEditingContext.()->Unit) : Journal {
	val context = JournalEditingContextImpl(journal = this)
	context.actions()

	return context.journal
}

interface JournalEditingContext {
	fun updateContents(contents: String)
	fun updateTitle(title: String)
	fun updateFeelings(feeling: Feelings)
	fun updateMoodFactor(moodFactor: MoodFactors)
}

private class JournalEditingContextImpl(var journal: Journal) : JournalEditingContext {
	override fun updateContents(contents: String) {
		journal = journal.copy(contents = contents)
	}

	override fun updateTitle(title: String) {
		journal = journal.copy(title = title)
	}

	override fun updateFeelings(feeling: Feelings) {
		journal = journal.copy(feeling = feeling)
	}

	override fun updateMoodFactor(moodFactor: MoodFactors) {
		journal = journal.copy(moodFactors = moodFactor)
	}

}