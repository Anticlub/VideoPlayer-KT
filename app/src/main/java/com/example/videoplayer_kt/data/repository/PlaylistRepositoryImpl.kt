package com.example.videoplayer_kt.data.repository

import com.example.videoplayer_kt.data.local.datasource.PlaylistLocalDataSource
import com.example.videoplayer_kt.data.mapper.toDomain
import com.example.videoplayer_kt.data.mapper.toEntity
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor (
    private val localDataSource: PlaylistLocalDataSource
) : PlaylistRepository{

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return localDataSource.getAllPlaylists()
            .map { allPlaylist ->
                allPlaylist.map { it.toDomain() }
            }
    }

    override suspend fun insertPlaylist(playlist: Playlist): Long {
        val entities = playlist.toEntity()
        return localDataSource.insertPlaylist(entities)
    }

    override suspend fun getPlaylistById(playlistId: Long): Playlist? {
        return localDataSource.getPlaylistById(playlistId)?.toDomain()
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        val entities = playlist.toEntity()
        localDataSource.deletePlaylist(entities)
    }
}