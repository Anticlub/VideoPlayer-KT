package dev.anticlub.videoplayer.data.local.datasource

import dev.anticlub.videoplayer.data.local.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistLocalDataSource {
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long
    suspend fun getPlaylistById(playlistId: Long): PlaylistEntity?
    suspend fun deletePlaylist(playlist: PlaylistEntity)
}