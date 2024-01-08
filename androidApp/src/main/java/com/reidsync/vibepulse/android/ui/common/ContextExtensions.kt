package com.reidsync.vibepulse.android.ui.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * Created by Reid on 2024/01/04.
 * Copyright (c) 2024 Reid Byun. All rights reserved.
 */


// https://github.com/google/accompanist/blob/6611ebda55eb2948eca9e1c89c2519e80300855a/permissions/src/main/java/com/google/accompanist/permissions/PermissionsUtil.kt#L43
/**
 * Find the closest Activity in a given Context.
 */
internal fun Context.findActivity(): Activity {
	var context = this
	while (context is ContextWrapper) {
		if (context is Activity) return context
		context = context.baseContext
	}
	throw IllegalStateException("Permissions should be called in the context of an Activity")
}