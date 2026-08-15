package dev.anticlub.videoplayer.data.repository

import dev.anticlub.videoplayer.data.local.datasource.ChannelLocalDataSource
import dev.anticlub.videoplayer.data.mapper.toDomain
import dev.anticlub.videoplayer.data.mapper.toEntity
import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(
    private val localDataSource: ChannelLocalDataSource
) : ChannelRepository{

    override fun getChannelsByPlaylist(playlistId: Long): Flow<List<Channel>> {
        return localDataSource.getChannelsByPlaylist(playlistId)
            .map { listOfEntities ->
                listOfEntities.map { it.toDomain() }
            }
    }

    override suspend fun clearAllChannels(playlistId: Long) {
        localDataSource.deleteChannelsByPlaylist(playlistId)
    }


    override suspend fun saveChannels(channels: List<Channel>, playlistId: Long) {
        val entities = channels.map { it.toEntity(playlistId) }
        localDataSource.insertChannels(entities)
    }

    override fun getFavoriteChannels(): Flow<List<Channel>> {
        return localDataSource.getFavoriteChannels()
            .map { listOfEntities ->
                listOfEntities.map { it.toDomain() }
            }
    }

    override suspend fun addToFavorites(channels: Channel) {
        localDataSource.updateFavorite(channels.id, true)
    }

    override suspend fun removeFromFavorites(channel: Channel) {
        localDataSource.updateFavorite(channel.id, false)
    }
}