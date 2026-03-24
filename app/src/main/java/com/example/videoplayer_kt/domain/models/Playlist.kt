package com.example.videoplayer_kt.domain.models

data class Playlist(
    val id: Long = 0,
    val name: String,
    val url: String? = null,
    val channels: List<Channel> = emptyList(),
    val lastUpdated: Long = System.currentTimeMillis()
)
