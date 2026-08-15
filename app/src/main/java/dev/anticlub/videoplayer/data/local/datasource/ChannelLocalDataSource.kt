package dev.anticlub.videoplayer.data.local.datasource

import dev.anticlub.videoplayer.data.local.entity.ChannelEntity
import kotlinx.coroutines.flow.Flow

interface ChannelLocalDataSource {
    fun getChannelsByPlaylist(playlistId: Long): Flow<List<ChannelEntity>>
    suspend fun insertChannels(channels: List<ChannelEntity>)
    suspend fun deleteChannelsByPlaylist(playlistId: Long)
    fun getFavoriteChannels(): Flow<List<ChannelEntity>>
    suspend fun updateFavorite(chanelId: Long, isFavorite: Boolean)
}