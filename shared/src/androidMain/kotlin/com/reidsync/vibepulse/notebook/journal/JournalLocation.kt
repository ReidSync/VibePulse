package com.reidsync.vibepulse.notebook.journal

actual fun JournalLocation.getString(): String {
	return String.format("(%.2f, %.2f)", latitude, longitude)
}