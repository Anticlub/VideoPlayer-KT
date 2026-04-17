package com.example.videoplayer_kt.domain.usecases.auth

import com.example.videoplayer_kt.domain.models.DataResult
import com.example.videoplayer_kt.domain.repository.AuthRepository

import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() : DataResult<Unit> {
        return repository.logout()
    }
}