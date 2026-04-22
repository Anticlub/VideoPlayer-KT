package com.example.videoplayer_kt.presentation.player

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import com.example.videoplayer_kt.domain.models.DrmConfig

object PlayerDrmManager {
    fun buildMediaItem(url: String, drmConfig: DrmConfig?): MediaItem {
        if (drmConfig == null) {
            return MediaItem.fromUri(url)
        } else {
            val drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                .setLicenseUri(drmConfig.licenseUrl)
                .setLicenseRequestHeaders(drmConfig.headers)
                .build()

            return MediaItem.Builder()
                .setUri(url)
                .setDrmConfiguration(drmConfiguration)
                .build()
        }
    }
}