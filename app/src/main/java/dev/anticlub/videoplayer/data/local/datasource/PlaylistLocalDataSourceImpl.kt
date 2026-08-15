package dev.anticlub.videoplayer.data.local.datasource

import dev.anticlub.videoplayer.data.local.entity.PlaylistDao
import dev.anticlub.videoplayer.data.local.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistLocalDataSourceImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistLocalDataSource{
    override fun getAllPlaylists(): Flow<List<PlaylistEntity>> {
        return playlistDao.getAllPlaylists()
    }

    override suspend fun insertPlaylist(playlist: PlaylistEntity): Long {
        return playlistDao.insertPlaylist(playlist)
    }

    override suspend fun getPlaylistById(playlistId: Long): PlaylistEntity? {
        return playlistDao.getPlaylistById(playlistId
        )
    }

    override suspend fun deletePlaylist(playlist: PlaylistEntity) {
        playlistDao.deletePlaylist(playlist)
    }
}