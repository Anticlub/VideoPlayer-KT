package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.repository.ChannelRepository
import javax.inject.Inject

class ClearAllChannelsUseCase @Inject constructor(
    private val repository: ChannelRepository
){
    suspend operator fun invoke(playlistId: Long){
        repository.clearAllChannels(playlistId)
    }
}