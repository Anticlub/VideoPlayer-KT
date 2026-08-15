package dev.anticlub.videoplayer.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.anticlub.videoplayer.data.local.UserPreferences
import dev.anticlub.videoplayer.domain.models.Playlist
import dev.anticlub.videoplayer.domain.usecases.auth.LogoutUseCase
import dev.anticlub.videoplayer.domain.usecases.channel.ClearAllChannelsUseCase
import dev.anticlub.videoplayer.domain.usecases.channel.SaveChannelUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.DeletePlaylistUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.DetectUrlTypeUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.DownloadPlaylistsUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.FetchPlaylistUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.GetPlaylistByIdUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.GetPlaylistUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.InsertPlaylistUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.ParsePlaylistUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.SyncPlaylistUseCase
import dev.anticlub.videoplayer.domain.usecases.playlist.UploadPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val getPlaylistByIdUseCase: GetPlaylistByIdUseCase,
    private val insertPlaylisUseCase: InsertPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val parsePlaylistUseCase: ParsePlaylistUseCase,
    private val fetchPlaylistUseCase: FetchPlaylistUseCase,
    private val userPreferences: UserPreferences,
    private val clearAllChannelsUseCase: ClearAllChannelsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val syncPlaylistUseCase: SyncPlaylistUseCase,
    private val uploadPlaylistsUseCase: UploadPlaylistsUseCase,
    private val downloadPlaylistsUseCase: DownloadPlaylistsUseCase,
    private val detectUrlTypeUseCase: DetectUrlTypeUseCase,
    private val saveChannelUseCase: SaveChannelUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlaylistUiState>(PlaylistUiState.Loading)
    val uiState: StateFlow<PlaylistUiState> = _uiState.asStateFlow()
    val lastChannelUrl: StateFlow<String> = userPreferences.lastChannelUrl
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )
    val lasChannelName: StateFlow<String> = userPreferences.lastChannelName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )
    val lastChannelLogo: StateFlow<String> = userPreferences.lastChannelLogo
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed((5000)),
            initialValue = ""
        )
    val lastChannelPlaylistName: StateFlow<String> = userPreferences.lastChannelPlaylist
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    init {
        downloadPlaylist()
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
                handleError(e)
            }
        }
    }

    fun insertPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            try {
                val playlistId = insertPlaylisUseCase(playlist)
                downloadAndParsePlaylist(playlist, playlistId)
                uploadPlaylistsUseCase(playlist.copy(id = playlistId))
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            try {
                deletePlaylistUseCase(playlist)
                val result = getPlaylistUseCase().first()
                syncPlaylistUseCase(result)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun downloadAndParsePlaylist(playlist: Playlist, playlistId: Long) {
        clearAllChannelsUseCase(playlistId)
        val channel = detectUrlTypeUseCase(playlist.url ?: "")
        if (channel != null) {
            saveChannelUseCase(channel, playlistId)
            return
        }
        val content = fetchPlaylistUseCase(playlist.url ?: "")
        if (!content.trimStart().startsWith("#EXTM3U")) {
            throw Exception("La URL no contiene una playlist M3U válida")
        }
        val updated = playlist.copy(
            id = playlistId,
            hasChannels = true
        )
        insertPlaylisUseCase(updated)
        parsePlaylistUseCase(content, playlistId)
    }

    fun editPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            try {
                val oldPlaylist = getPlaylistByIdUseCase(playlist.id)
                insertPlaylisUseCase(playlist)
                if (oldPlaylist?.url != playlist.url) {
                    downloadAndParsePlaylist(playlist, playlist.id)
                }
                uploadPlaylistsUseCase(playlist)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }


    fun downloadPlaylist() {
        viewModelScope.launch {
            try {
                downloadPlaylistsUseCase().map { playlist ->
                    val exists = getPlaylistByIdUseCase(playlist.id)
                    if (exists == null) {
                        val playlistId = insertPlaylisUseCase(playlist)
                        downloadAndParsePlaylist(playlist, playlistId)
                    }
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception) {
        _uiState.value = PlaylistUiState.Error(e.message ?: "Unknown error")
    }
}