package dev.anticlub.videoplayer.domain.usecases.channel

import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import javax.inject.Inject

class SaveChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(channel: Channel, playlistId: Long) {
        return repository.saveChannels(listOf(channel), playlistId)
    }
}