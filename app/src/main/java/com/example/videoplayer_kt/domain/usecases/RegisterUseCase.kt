package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.AuthResult
import com.example.videoplayer_kt.domain.models.User
import com.example.videoplayer_kt.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): AuthResult<User>{
        return repository.register(email, password)
    }
}