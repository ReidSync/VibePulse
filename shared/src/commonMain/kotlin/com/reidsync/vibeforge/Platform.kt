package com.reidsync.vibeforge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform