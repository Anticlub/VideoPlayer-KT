package com.example.videoplayer_kt.data.repository

import com.example.videoplayer_kt.data.local.entity.ChannelDao
import com.example.videoplayer_kt.data.mapper.toDomain
import com.example.videoplayer_kt.data.mapper.toEntity
import com.example.videoplayer_kt.domain.models.Channel
import com.example.videoplayer_kt.domain.repository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(private val channelDao: ChannelDao) : ChannelRepository{

    override fun getChannelsByPlaylist(playlistId: Long): Flow<List<Channel>> {
        return channelDao.getChannelsByPlaylist(playlistId)
            .map { listOfEntities ->
                listOfEntities.map { it.toDomain() }
            }
    }


    override suspend fun clearAllChannels(playlistId: Long) {
        channelDao.deleteChannelsByPlaylist(playlistId)
    }


    override suspend fun saveChannels(channels: List<Channel>, playlistId: Long) {
        val entities = channels.map { it.toEntity(playlistId) }
        channelDao.insertChannels(entities)
    }


}