package com.example.videoplayer_kt.domain.usecases.channel

import com.example.videoplayer_kt.domain.models.Channel
import com.example.videoplayer_kt.domain.repository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteChannelsUseCase @Inject constructor(
    private val repository: ChannelRepository
){
    operator fun invoke() : Flow<List<Channel>>{
        return repository.getFavoriteChannels()
    }
}