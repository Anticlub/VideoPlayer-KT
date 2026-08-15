package dev.anticlub.videoplayer.domain.models

sealed class PlaybackError {
    object Network : PlaybackError()
    object Source : PlaybackError()
    object Drm : PlaybackError()
    data class Unknown(val message: String?) : PlaybackError()
}