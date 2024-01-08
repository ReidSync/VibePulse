package com.reidsync.vibepulse.notebook.journal

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun JournalLocation.getString(): String {
	return NSString.stringWithFormat("%.2f, %.2f", latitude, longitude)
}