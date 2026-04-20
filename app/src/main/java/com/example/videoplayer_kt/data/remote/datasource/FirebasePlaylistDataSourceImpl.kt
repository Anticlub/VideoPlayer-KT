package com.example.videoplayer_kt.data.remote.datasource

import com.example.videoplayer_kt.data.remote.dto.PlaylistDto
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FirebasePlaylistDataSourceImpl @Inject constructor(
    private val firebaseDataBase: FirebaseDatabase,
    private val uidUser: FirebaseAuth
) : PlaylistRemoteDataSource{
    override suspend fun syncPlaylists(playlists: List<PlaylistDto>) {
        TODO("Not yet implemented")
    }

    override suspend fun uploadPlaylist(playlist: PlaylistDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        TODO("Not yet implemented")
    }
}