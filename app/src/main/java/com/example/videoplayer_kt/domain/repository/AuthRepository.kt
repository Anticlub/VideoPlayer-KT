package com.example.videoplayer_kt.domain.repository

import com.example.videoplayer_kt.domain.models.DataResult
import com.example.videoplayer_kt.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String) : DataResult<User>
    suspend fun register(email: String, password: String): DataResult<User>
    suspend fun logout(): DataResult<Unit>
    fun getCurrentUser(): User?
    fun observeAuthState(): Flow<User?>
}