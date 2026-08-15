package dev.anticlub.videoplayer.data.local.datasource

import dev.anticlub.videoplayer.data.local.entity.ChannelDao
import dev.anticlub.videoplayer.data.local.entity.ChannelEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChannelLocalDataSourceImp @Inject constructor(
    private val channelDao: ChannelDao
) : ChannelLocalDataSource {
    override fun getChannelsByPlaylist(playlistId: Long): Flow<List<ChannelEntity>> {
        return channelDao.getChannelsByPlaylist(playlistId)
    }

    override suspend fun insertChannels(channels: List<ChannelEntity>) {
        channelDao.insertChannels(channels)
    }

    override suspend fun deleteChannelsByPlaylist(playlistId: Long) {
        channelDao.deleteChannelsByPlaylist(playlistId)
    }

    override fun getFavoriteChannels(): Flow<List<ChannelEntity>> {
        return  channelDao.getFavoriteChannels()
    }

    override suspend fun updateFavorite(chanelId: Long, isFavorite: Boolean) {
        channelDao.updateFavorites(chanelId, isFavorite)
    }

}