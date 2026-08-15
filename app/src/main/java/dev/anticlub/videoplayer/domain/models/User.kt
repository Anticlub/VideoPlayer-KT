package dev.anticlub.videoplayer.domain.models

data class User (
    val uid: String = "",
    val email: String = "",
    val displayName: String? = null
)