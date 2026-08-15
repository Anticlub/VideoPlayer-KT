package dev.anticlub.videoplayer.data.mapper

import dev.anticlub.videoplayer.data.local.entity.ChannelEntity
import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.models.DrmConfig

fun ChannelEntity.toDomain(): Channel {
    return Channel(
        id = id,
        tvgId = tvgId,
        name = name,
        url = url,
        group = group,
        logo = logo,
        isFavorite = isFavorite,
        drmConfig = licenseUrl?.let { DrmConfig(licenseUrl = it) }
    )
}

fun Channel.toEntity(playlistId: Long): ChannelEntity {
    return ChannelEntity(
        id = id,
        tvgId = tvgId,
        name = name,
        url = url,
        group = group,
        logo = logo,
        isFavorite = isFavorite,
        playlistId = playlistId,
        licenseUrl = drmConfig?.licenseUrl
    )
}