package dev.anticlub.videoplayer.domain.usecases.channel

import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import javax.inject.Inject

class ClearAllChannelsUseCase @Inject constructor(
    private val repository: ChannelRepository
){
    suspend operator fun invoke(playlistId: Long){
        repository.clearAllChannels(playlistId)
    }
}