package com.example.videoplayer_kt.data.remote.datasource

import com.example.videoplayer_kt.data.remote.dto.PlaylistDto
import com.example.videoplayer_kt.domain.models.AuthErrorType
import com.example.videoplayer_kt.domain.models.DomainException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.contracts.contract
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebasePlaylistDataSourceImpl @Inject constructor(
    private val firebaseDataBase: FirebaseDatabase,
    private val uidUser: FirebaseAuth
) : PlaylistRemoteDataSource{
    override suspend fun syncPlaylists(playlists: List<PlaylistDto>) {
        val uid = uidUser.currentUser?.uid ?: throw DomainException.AuthException(AuthErrorType.USER_NOT_FOUND)
        val ref = firebaseDataBase.getReference("users")
            .child(uid)
            .child("playlists")

        suspendCancellableCoroutine { continuation ->
            ref.setValue(playlists)
                .addOnSuccessListener { continuation.resume(Unit) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }

    override suspend fun uploadPlaylist(playlist: PlaylistDto) {
        val uid = uidUser.currentUser?.uid ?: throw DomainException.AuthException(AuthErrorType.USER_NOT_FOUND)
        val ref = firebaseDataBase.getReference("users")
            .child(uid)
            .child("playlists")
            .child(playlist.id.toString())

        suspendCancellableCoroutine { continuation ->
            ref.setValue(playlist)
                .addOnSuccessListener { continuation.resume(Unit) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        val uid = uidUser.currentUser?.uid ?: throw DomainException.AuthException(AuthErrorType.USER_NOT_FOUND)
        val ref = firebaseDataBase.getReference("users")
            .child(uid)
            .child("playlists")
            .child(playlistId.toString())

        suspendCancellableCoroutine { continuation ->
            ref.removeValue()
                .addOnSuccessListener { continuation.resume(Unit) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }

    override suspend fun downloadPlaylists(): List<PlaylistDto> {
        val uid = uidUser.currentUser?.uid ?: throw DomainException.AuthException(AuthErrorType.USER_NOT_FOUND)
        val ref = firebaseDataBase.getReference("users")
            .child(uid)
            .child("playlists")

        return suspendCancellableCoroutine { continuation ->
            ref.get()
                .addOnSuccessListener { snapshot ->
                    val playlists = snapshot.children
                        .mapNotNull { it.getValue(PlaylistDto::class.java) }
                    continuation.resume(playlists)
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }
}