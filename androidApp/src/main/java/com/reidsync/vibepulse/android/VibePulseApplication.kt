package com.reidsync.vibepulse.android

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.staticCompositionLocalOf
import com.reidsync.vibepulse.android.di.AppContainer
import com.reidsync.vibepulse.android.di.DefaultAppContainer
import com.reidsync.vibepulse.primitives.colors.RC

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

lateinit var APP: VibePulseApplication
	private set

// https://developer.android.com/jetpack/compose/compositionlocal
@SuppressLint("CompositionLocalNaming")
val AppThemeColor = staticCompositionLocalOf { RC.default }

class VibePulseApplication: Application() {
	lateinit var container: AppContainer

	override fun onCreate() {
		APP = this
		super.onCreate()
		container = DefaultAppContainer()
	}
}