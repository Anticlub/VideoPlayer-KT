package com.example.videoplayer_kt.domain.models

data class DrmConfig(
    val licenseUrl: String,
    val headers: Map<String, String> = emptyMap()
)