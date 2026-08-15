package dev.anticlub.videoplayer.domain.repository

import dev.anticlub.videoplayer.domain.models.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    fun getChannelsByPlaylist(playlistId: Long): Flow<List<Channel>>
    suspend fun clearAllChannels(playlistId: Long)
    suspend fun saveChannels(channels: List<Channel>, playlistId: Long)
    suspend fun addToFavorites(channels: Channel)
    suspend fun removeFromFavorites(channel: Channel)
    fun getFavoriteChannels(): Flow<List<Channel>>
}