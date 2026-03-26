package com.example.videoplayer_kt.data.repository

import com.example.videoplayer_kt.data.local.entity.PlaylistDao
import com.example.videoplayer_kt.data.mapper.toDomain
import com.example.videoplayer_kt.data.mapper.toEntity
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistRepositoryImpl(private val playlistDao: PlaylistDao) : PlaylistRepository{

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists()
            .map { allPlaylist ->
                allPlaylist.map { it.toDomain() }
            }
    }

    override suspend fun insertPlaylist(playlist: Playlist) {
        val entities = playlist.toEntity()
        playlistDao.insertPlaylist(entities)
    }

    override suspend fun getPlaylistById(playlistId: Long): Playlist? {
        return playlistDao.getPlaylistById(playlistId)?.toDomain()
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        val entities = playlist.toEntity()
        playlistDao.deletePlaylist(entities)
    }
}