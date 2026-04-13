package com.example.videoplayer_kt.presentation.channel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer_kt.domain.models.Channel
import com.example.videoplayer_kt.domain.usecases.GetChannelsUseCase
import com.example.videoplayer_kt.domain.usecases.GetPlaylistByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.checkOffsetAndCount
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(

    private val getChannelsUseCase: GetChannelsUseCase,
    private val getPlaylistByIdUseCase: GetPlaylistByIdUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow<ChannelUiState>(ChannelUiState.Loading)
    private val _playlistName = MutableStateFlow("")
    val uiState: StateFlow<ChannelUiState> = _uiState.asStateFlow()
    val playlistName: StateFlow<String> = _playlistName.asStateFlow()
    private var allChannels: List<Channel> = emptyList()
    private var selectedGroup: String? = null



     fun loadChannel(playlistId: Long) {
         getPlaylistName(playlistId)
        viewModelScope.launch {
            _uiState.value = ChannelUiState.Loading
            try {
                getChannelsUseCase(playlistId).collect { channels ->
                    allChannels = channels
                    _uiState.value = ChannelUiState.Success(channels)
                }
            } catch (e: Exception) {
                _uiState.value = ChannelUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchChannels(query: String) {
        _uiState.value = ChannelUiState.Loading
        viewModelScope.launch (Dispatchers.Default){
            val filtered = allChannels.filter { channel ->
                val matchesQuery = query.isEmpty() ||
                        channel.name.contains(query, ignoreCase = true)
                val matchesGroup = selectedGroup == null ||
                        channel.group == selectedGroup
                matchesQuery && matchesGroup
            }
            _uiState.value = ChannelUiState.Success(filtered)
        }
    }

    fun filterByGroup(group: String?) {
        selectedGroup = group
        _uiState.value = ChannelUiState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            val filtered = allChannels.filter { channel ->
                group == null || channel.group == group
            }
            withContext(Dispatchers.Main) {
                _uiState.value = ChannelUiState.Success(filtered)
            }
        }
    }

    fun getPlaylistName(playlistId: Long) {
        viewModelScope.launch {
            val playlist = getPlaylistByIdUseCase(playlistId)
            _playlistName.value = playlist?.name ?: ""
        }
    }
}