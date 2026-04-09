package com.example.videoplayer_kt.presentation.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.usecases.DeletePlaylistUseCase
import com.example.videoplayer_kt.domain.usecases.FetchPlaylistUseCase
import com.example.videoplayer_kt.domain.usecases.GetPlaylistByIdUseCase
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
    private val getPlaylistByIdUseCase: GetPlaylistByIdUseCase,
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
        Log.d("PlaylistViewModel", "insertPlaylist llamado: ${playlist.url}")
        viewModelScope.launch {
            try {
                val playlistId = insertPlaylisUseCase(playlist)
                downloadAndParsePlaylist(playlist, playlistId)
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error al insertar playlist: ${e.message}")
                _uiState.value = PlaylistUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            deletePlaylistUseCase(playlist)
        }
    }

    private suspend fun downloadAndParsePlaylist(playlist: Playlist, playlistId: Long) {
        val content = fetchPlaylistUseCase(playlist.url ?: "")
        Log.d("PlaylistViewModel", "Primeras letras: ${content.take(50)}")
        if (!content.trimStart().startsWith("#EXTM3U")){
            throw Exception("La URL no contiene una playlist M3U válida")
        }
        val updated = playlist.copy(
            id = playlistId,
            hasChannels = true
        )
        Log.d("PlaylistViewModel", "Antes de parsear")
        insertPlaylisUseCase(updated)
        Log.d("PlaylistViewModel", "Antes de parsePlaylistUseCase")
        parsePlaylistUseCase(content, playlistId)
    }

    fun parsePlaylist(content: String, playlistId: Long) {
        viewModelScope.launch {
            parsePlaylistUseCase(content, playlistId)
        }
    }

    fun editPlaylist(playlist: Playlist){
        viewModelScope.launch {
            try {
                val oldPlaylist = getPlaylistByIdUseCase(playlist.id)
                insertPlaylisUseCase(playlist)
                if (oldPlaylist?.url != playlist.url) {
                    downloadAndParsePlaylist(playlist, playlist.id)
                }
            } catch (e: Exception) {
                _uiState.value = PlaylistUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}