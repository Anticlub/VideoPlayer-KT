package com.example.videoplayer_kt.domain.repository

import com.example.videoplayer_kt.domain.models.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    fun getAllChannels(): Flow<List<Channel>>
    fun getChannelsByGroup(group: String): Flow<List<Channel>>
    fun getFavoriteChannels(): Flow<List<Channel>>
    suspend fun addToFavorites(channel: Channel)
    suspend fun removeFromFavorites(channel: Channel)
    suspend fun saveChannels(channel: List<Channel>)
    suspend fun clearAllChannels()
}