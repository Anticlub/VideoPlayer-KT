package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.User
import com.example.videoplayer_kt.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke() : User? {
        return repository.getCurrentUser()
    }
}