package com.example.videoplayer_kt.presentation.channel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer_kt.domain.usecases.GetChannelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val getChannelsUseCase: GetChannelsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChannelUiState>(ChannelUiState.Loading)
    val uiState: StateFlow<ChannelUiState> = _uiState.asStateFlow()


     fun loadChannel(playlistId: Long) {
        viewModelScope.launch {
            _uiState.value = ChannelUiState.Loading
            try {
                getChannelsUseCase(playlistId).collect { channels ->
                    _uiState.value = ChannelUiState.Success(channels)
                }
            } catch (e: Exception) {
                _uiState.value = ChannelUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}