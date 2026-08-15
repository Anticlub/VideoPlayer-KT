package dev.anticlub.videoplayer.data.mapper

import dev.anticlub.videoplayer.data.local.entity.PlaylistEntity
import dev.anticlub.videoplayer.domain.models.Playlist
import dev.anticlub.videoplayer.domain.models.PlaylistType

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