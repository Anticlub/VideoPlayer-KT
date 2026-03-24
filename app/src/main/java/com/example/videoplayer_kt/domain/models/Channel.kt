package com.example.videoplayer_kt.domain.models

data class Channel(
    val id: Long = 0,
    val name: String,
    val url: String,
    val group: String? = null,
    val logo: String? = null,
    val isFavorite: Boolean = false
)
