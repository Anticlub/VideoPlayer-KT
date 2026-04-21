package com.example.videoplayer_kt.data.mapper

import com.example.videoplayer_kt.data.remote.dto.PlaylistDto
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.models.PlaylistType

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