package com.example.videoplayer_kt.data.remote.datasource

import com.example.videoplayer_kt.data.remote.dto.PlaylistDto
import com.example.videoplayer_kt.domain.models.Playlist

interface PlaylistRemoteDataSource {
    suspend fun syncPlaylists(playlists: List<PlaylistDto>)
    suspend fun uploadPlaylist(playlist: PlaylistDto)
    suspend fun deletePlaylist(playlistId: Long)
    suspend fun downloadPlaylists(): List<PlaylistDto>
}