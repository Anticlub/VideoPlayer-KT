package com.example.videoplayer_kt.data.mapper

import com.example.videoplayer_kt.data.local.entity.PlaylistEntity
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.models.PlaylistType

fun PlaylistEntity.toDomain(): Playlist {
    return Playlist(
        id = id,
        name = name,
        url = url,
        type = PlaylistType.valueOf(type),
        lastUpdated = lastUpdated,
        hasChannels = hasChannels
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = id,
        name = name,
        url = url ?: "",
        type = type.name,
        lastUpdated = lastUpdated,
        hasChannels = hasChannels
    )
}