package com.reidsync.vibepulse.util

import platform.Foundation.NSDateFormatter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

/**
 * Created by Reid on 2023/12/18.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */


actual fun LocalDateTime.format(format: String): String {
//	val dateFormatter = NSDateFormatter()
//	dateFormatter.dateFormat = format
//	// Parse the UTC timestamp into an NSDate
//	val date = this.toInstant(TimeZone.UTC) .toNSDate()
//	// Create a date formatter for the local time zone
//	dateFormatter.timeZone = NSTimeZone.localTimeZone
//	dateFormatter.dateFormat = format
//	// Format the NSDate into a string in the local time zone
//	return dateFormatter.stringFromDate(date)

	val dateFormatter = NSDateFormatter()
	dateFormatter.dateFormat = format
	dateFormatter.timeZone = NSTimeZone.localTimeZone
	val date = this.toInstant(TimeZone.currentSystemDefault()).toNSDate()
	return dateFormatter.stringFromDate(date)
}