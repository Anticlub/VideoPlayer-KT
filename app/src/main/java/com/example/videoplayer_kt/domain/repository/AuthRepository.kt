package com.example.videoplayer_kt.domain.repository

import com.example.videoplayer_kt.domain.models.AuthResult
import com.example.videoplayer_kt.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String) : AuthResult<User>
    suspend fun register(email: String, password: String): AuthResult<User>
    suspend fun logout(): AuthResult<Unit>
    fun getCurrentUser(): User?
    fun observeAuthState(): Flow<User?>
}