package dev.anticlub.videoplayer.data.repository

import dev.anticlub.videoplayer.data.local.datasource.PlaylistLocalDataSource
import dev.anticlub.videoplayer.data.mapper.toDomain
import dev.anticlub.videoplayer.data.mapper.toDto
import dev.anticlub.videoplayer.data.mapper.toEntity
import dev.anticlub.videoplayer.data.remote.datasource.PlaylistRemoteDataSource
import dev.anticlub.videoplayer.domain.models.Playlist
import dev.anticlub.videoplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor (
    private val localDataSource: PlaylistLocalDataSource,
    private val remoteDataSource: PlaylistRemoteDataSource
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

    override suspend fun syncPlaylist(playlist: List<Playlist>) {
        val result = playlist.map { playlist ->
            playlist.toDto()
        }
        remoteDataSource.syncPlaylists(result)
    }

    override suspend fun uploadPlaylist(playlist: Playlist) {
        val result = playlist.toDto()
        remoteDataSource.uploadPlaylist(result)
    }

    override suspend fun downloadPlaylist(): List<Playlist> {
        val result = remoteDataSource.downloadPlaylists()
        return result.map { it.toDomain() }
    }
}