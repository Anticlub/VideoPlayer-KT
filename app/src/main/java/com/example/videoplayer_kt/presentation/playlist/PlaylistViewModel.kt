package com.example.videoplayer_kt.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.usecases.DeletePlaylistUseCase
import com.example.videoplayer_kt.domain.usecases.FetchPlaylistUseCase
import com.example.videoplayer_kt.domain.usecases.GetPlaylistUseCase
import com.example.videoplayer_kt.domain.usecases.InsertPlaylistUseCase
import com.example.videoplayer_kt.domain.usecases.ParsePlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val insertPlaylisUseCase: InsertPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val parsePlaylistUseCase: ParsePlaylistUseCase,
    private val fetchPlaylistUseCase: FetchPlaylistUseCase
): ViewModel() {

    // Estado de la lista de playlist, solo el viewmodel puede modificarlo
    private val _uiState = MutableStateFlow<PlaylistUiState>(PlaylistUiState.Loading)
    val uiState: StateFlow<PlaylistUiState> = _uiState.asStateFlow()

    init {
        loadPlaylists()
    }

    private fun loadPlaylists() {
        viewModelScope.launch {
            _uiState.value = PlaylistUiState.Loading
            try {
                getPlaylistUseCase().collect { list ->
                    _uiState.value = PlaylistUiState.Success(list)
                }
            } catch (e: Exception) {
                _uiState.value = PlaylistUiState.Error(e.message ?: "Unknown error")
            }

        }
    }

    fun insertPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            try {
                // 1. Insertar la playlist y obtener el id generado
                val playlistId = insertPlaylisUseCase(playlist)
                // 2. Descargar el contenido de la URL
                val content = fetchPlaylistUseCase(playlist.url ?: "")
                // 3. Parsear y guardar los canales
                parsePlaylistUseCase(content, playlistId)
            } catch (e: Exception) {
                _uiState.value = PlaylistUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            deletePlaylistUseCase(playlist)
        }
    }

    fun parsePlaylist(content: String, playlistId: Long) {
        viewModelScope.launch {
            parsePlaylistUseCase(content, playlistId)
        }
    }

    fun editPlaylist(playlist: Playlist){
        viewModelScope.launch {
            insertPlaylisUseCase(playlist)
        }
    }
}