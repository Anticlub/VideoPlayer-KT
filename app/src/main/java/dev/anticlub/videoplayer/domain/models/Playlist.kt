package dev.anticlub.videoplayer.domain.models

data class Playlist(
    val id: Long = 0,
    val name: String,
    val url: String? = null,
    val type: PlaylistType = PlaylistType.M3U,
    val channels: List<Channel> = emptyList(),
    val lastUpdated: Long = System.currentTimeMillis(),
    val hasChannels: Boolean = false
)
