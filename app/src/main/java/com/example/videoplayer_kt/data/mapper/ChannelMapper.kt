package com.example.videoplayer_kt.data.mapper

import com.example.videoplayer_kt.data.local.entity.ChannelEntity
import com.example.videoplayer_kt.domain.models.Channel

fun ChannelEntity.toDomain(): Channel {
    return Channel(
        id = id,
        tvgId = tvgId,
        name = name,
        url = url,
        group = group,
        logo = logo,
        isFavorite = isFavorite
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
        playlistId = playlistId
    )
}