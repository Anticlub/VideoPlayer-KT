package dev.anticlub.videoplayer.domain.usecases.channel

import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repository: ChannelRepository
){
    suspend operator fun invoke(channel: Channel) {
        return repository.addToFavorites(channel)
    }
}