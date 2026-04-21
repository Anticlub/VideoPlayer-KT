package com.example.videoplayer_kt.data.remote.datasource

import com.example.videoplayer_kt.data.remote.dto.PlaylistDto

interface PlaylistRemoteDataSource {
    suspend fun syncPlaylists(playlists: List<PlaylistDto>)
    suspend fun uploadPlaylist(playlist: PlaylistDto)
    suspend fun downloadPlaylists(): List<PlaylistDto>
}