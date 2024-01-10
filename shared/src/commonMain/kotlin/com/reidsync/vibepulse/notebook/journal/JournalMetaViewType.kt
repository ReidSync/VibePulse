package com.reidsync.vibepulse.notebook.journal

/**
 * Created by Reid on 2024/01/10.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */

sealed class JournalMetaViewType {
	data object Add : JournalMetaViewType()
	data object Edit : JournalMetaViewType()
}