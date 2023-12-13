package com.reidsync.vibepulse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform