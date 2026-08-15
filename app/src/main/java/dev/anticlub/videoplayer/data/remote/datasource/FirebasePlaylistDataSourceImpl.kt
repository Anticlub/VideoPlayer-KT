package dev.anticlub.videoplayer.data.remote.datasource

import dev.anticlub.videoplayer.data.remote.dto.PlaylistDto
import dev.anticlub.videoplayer.domain.models.AuthErrorType
import dev.anticlub.videoplayer.domain.models.DomainException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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
            val map = playlists.associate { it.id.toString() to it }
            ref.setValue(map)
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