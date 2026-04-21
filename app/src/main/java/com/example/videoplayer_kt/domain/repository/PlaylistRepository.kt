package com.example.videoplayer_kt.domain.repository

import com.example.videoplayer_kt.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    suspend fun insertPlaylist(playlist: Playlist) : Long
    suspend fun getPlaylistById(playlistId: Long): Playlist?
    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun syncPlaylist(playlist: List<Playlist>)
    suspend fun uploadPlaylist(playlist: Playlist)
    suspend fun downloadPlaylist(): List<Playlist>
}