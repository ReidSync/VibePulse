package com.reidsync.vibepulse.android

import android.app.Application
import com.reidsync.vibepulse.android.data.AppContainer
import com.reidsync.vibepulse.android.data.DefaultAppContainer

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

object VibePulseApplication: Application() {
	lateinit var container: AppContainer

	override fun onCreate() {
		super.onCreate()
		container = DefaultAppContainer()
	}
}