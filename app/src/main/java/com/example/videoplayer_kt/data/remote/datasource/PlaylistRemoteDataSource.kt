package com.example.videoplayer_kt.data.remote.datasource

import com.example.videoplayer_kt.domain.models.Playlist

interface PlaylistRemoteDataSource {
    suspend fun syncPlaylists(playlists: List<Playlist>): Unit
}