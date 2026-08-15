package dev.anticlub.videoplayer.domain.repository

import dev.anticlub.videoplayer.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String) : User
    suspend fun register(email: String, password: String): User
    suspend fun logout()
    fun getCurrentUser(): User?
    fun observeAuthState(): Flow<User?>
}