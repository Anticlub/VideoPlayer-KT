package dev.anticlub.videoplayer.presentation.channel

import dev.anticlub.videoplayer.domain.models.Channel

sealed class ChannelUiState {
    object Loading : ChannelUiState()
    data class Success(val channels: List<Channel>) : ChannelUiState()
    data class Error(val message: String) : ChannelUiState()
}