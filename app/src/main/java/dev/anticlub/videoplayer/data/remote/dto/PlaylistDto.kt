package dev.anticlub.videoplayer.data.remote.dto

import dev.anticlub.videoplayer.domain.models.PlaylistType

data class PlaylistDto (
    val id: Long = 0,
    val name: String = "",
    val url: String = "",
    val type: String = PlaylistType.M3U.name
)