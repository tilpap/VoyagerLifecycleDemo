package com.example.voyagerlifecycledemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform