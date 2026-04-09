package com.example.videoplayer_kt.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(

) : ViewModel(){

    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Loading)
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun initPlayer(url: String) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
        }
    }
    fun onBuffering() { _uiState.value = PlayerUiState.Loading }
    fun onPlaying() { _uiState.value = PlayerUiState.Playing }
    fun onEnded() { _uiState.value = PlayerUiState.Ended }
    fun onError(message: String) { _uiState.value = PlayerUiState.Error(message) }
}
