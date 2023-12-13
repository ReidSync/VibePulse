package com.reidsync.vibeforge.primitives.uuid

/**
 * Created by Reid on 2023/12/12.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

import platform.Foundation.NSUUID

actual fun UUID.Companion.randomUUID(): UUID = UUID(NSUUID().UUIDString)!!