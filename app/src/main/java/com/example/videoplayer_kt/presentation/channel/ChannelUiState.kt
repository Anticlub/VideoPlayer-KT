package com.example.videoplayer_kt.presentation.channel

import com.example.videoplayer_kt.domain.models.Channel

sealed class ChannelUiState {
    object Loading : ChannelUiState()
    data class Success(val channels: List<Channel>) : ChannelUiState()
    data class Error(val message: String) : ChannelUiState()
}