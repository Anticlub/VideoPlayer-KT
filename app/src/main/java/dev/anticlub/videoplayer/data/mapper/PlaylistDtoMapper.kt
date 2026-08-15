package dev.anticlub.videoplayer.data.mapper

import dev.anticlub.videoplayer.data.remote.dto.PlaylistDto
import dev.anticlub.videoplayer.domain.models.Playlist
import dev.anticlub.videoplayer.domain.models.PlaylistType

fun PlaylistDto.toDomain(): Playlist {
    return Playlist(
        id = id,
        name = name,
        url = url,
        type = PlaylistType.valueOf(type)
    )
}

fun Playlist.toDto(): PlaylistDto {
    return PlaylistDto(
        id = id,
        name = name,
        url = url ?: "",
        type = type.name
    )
}