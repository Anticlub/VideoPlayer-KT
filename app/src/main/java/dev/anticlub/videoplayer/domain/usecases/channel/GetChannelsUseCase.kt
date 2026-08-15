package dev.anticlub.videoplayer.domain.usecases.channel

import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChannelsUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    operator fun invoke(playlistId: Long): Flow<List<Channel>> {
        return repository.getChannelsByPlaylist(playlistId)
    }
}