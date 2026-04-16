package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.User
import com.example.videoplayer_kt.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke() : Flow<User?>{
        return repository.observeAuthState()
    }
}