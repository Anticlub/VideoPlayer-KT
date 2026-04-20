package com.example.videoplayer_kt.data.remote.dto

import com.example.videoplayer_kt.domain.models.PlaylistType

data class PlaylistDto (
    val id: Long = 0,
    val name: String = "",
    val url: String = "",
    val type: String = PlaylistType.M3U.name
)