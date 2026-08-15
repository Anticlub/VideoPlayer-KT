package dev.anticlub.videoplayer.data.mapper

import androidx.media3.common.PlaybackException
import dev.anticlub.videoplayer.domain.models.PlaybackError

object PlaybackErrorMapper {
    fun map(error: PlaybackException) : PlaybackError {
        return when (error.errorCode) {
            PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR,
            PlaybackException.ERROR_CODE_DRM_LICENSE_EXPIRED,
            PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR -> PlaybackError.Drm
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> PlaybackError.Network
            PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS,
            PlaybackException.ERROR_CODE_PARSING_CONTAINER_MALFORMED,
            PlaybackException.ERROR_CODE_PARSING_MANIFEST_MALFORMED -> PlaybackError.Source
            else -> PlaybackError.Unknown(error.localizedMessage)

        }
    }
}