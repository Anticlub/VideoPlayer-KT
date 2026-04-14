package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.Channel
import com.example.videoplayer_kt.domain.repository.ChannelRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(channel: Channel) {
        return repository.removeFromFavorites(channel)
    }
}