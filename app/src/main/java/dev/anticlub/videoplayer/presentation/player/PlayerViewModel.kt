package dev.anticlub.videoplayer.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.PlaybackException
import dev.anticlub.videoplayer.data.local.UserPreferences
import dev.anticlub.videoplayer.data.mapper.PlaybackErrorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Loading)
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun initPlayer(url: String, name: String, logo: String, playlist: String) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            userPreferences.saveLastChannel(url, name, logo, playlist)
        }
    }

    fun onBuffering() {
        _uiState.value = PlayerUiState.Loading
    }

    fun onPlaying() {
        _uiState.value = PlayerUiState.Playing
    }

    fun onEnded() {
        _uiState.value = PlayerUiState.Ended
    }

    fun onError(error: PlaybackException) {
        _uiState.value = PlayerUiState.Error(PlaybackErrorMapper.map(error))
    }
}
