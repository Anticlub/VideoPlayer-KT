package com.example.videoplayer_kt.domain.util

object StreamValidator {

    private val EXTENSIONS = listOf(".m3u8", ".mpd", ".ts")

    fun isDirectStreamUrl(url: String): Boolean {
        return EXTENSIONS.any { url.substringBefore("?").endsWith(it) }
    }
}
