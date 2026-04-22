package com.example.videoplayer_kt.data.local.datasource

import com.example.videoplayer_kt.data.local.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistLocalDataSource {
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long
    suspend fun getPlaylistById(playlistId: Long): PlaylistEntity?
    suspend fun deletePlaylist(playlist: PlaylistEntity)
}