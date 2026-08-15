package dev.anticlub.videoplayer.domain.models

data class Channel(
    val id: Long = 0,
    val tvgId: String? = null,
    val name: String,
    val url: String,
    val group: String? = null,
    val logo: String? = null,
    val isFavorite: Boolean = false,
    val drmConfig: DrmConfig? = null
)
