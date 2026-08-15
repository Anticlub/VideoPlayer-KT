package dev.anticlub.videoplayer.data.remote.datasource

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String) : FirebaseUser
    suspend fun register(email: String, password: String): FirebaseUser
    suspend fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun observeAuthState(): Flow<FirebaseUser?>
}