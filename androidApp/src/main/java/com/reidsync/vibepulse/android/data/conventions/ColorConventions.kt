package com.reidsync.vibepulse.android.data.conventions

import androidx.compose.ui.graphics.Color
import com.reidsync.vibepulse.primitives.colors.SolidColor

/**
 * Created by Reid on 2023/12/22.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

fun SolidColor.toColor() : Color = Color(r,g,b,a)