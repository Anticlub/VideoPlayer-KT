package com.example.videoplayer_kt.domain.usecases.auth

import com.example.videoplayer_kt.domain.models.User
import com.example.videoplayer_kt.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): User {
        return repository.login(email, password)
    }
}