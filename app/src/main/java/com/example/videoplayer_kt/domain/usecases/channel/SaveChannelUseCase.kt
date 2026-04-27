package com.example.videoplayer_kt.domain.usecases.channel

import com.example.videoplayer_kt.domain.models.Channel
import com.example.videoplayer_kt.domain.repository.ChannelRepository
import javax.inject.Inject

class SaveChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(channel: Channel, playlistId: Long) {
        return repository.saveChannels(listOf(channel), playlistId)
    }
}