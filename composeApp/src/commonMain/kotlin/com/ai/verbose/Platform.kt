package com.ai.verbose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform