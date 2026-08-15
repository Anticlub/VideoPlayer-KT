package dev.anticlub.videoplayer.domain.models

data class DrmConfig(
    val licenseUrl: String,
    val headers: Map<String, String> = emptyMap()
)