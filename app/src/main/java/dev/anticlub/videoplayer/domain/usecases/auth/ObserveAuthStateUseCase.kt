package dev.anticlub.videoplayer.domain.usecases.auth

import dev.anticlub.videoplayer.domain.models.User
import dev.anticlub.videoplayer.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke() : Flow<User?>{
        return repository.observeAuthState()
    }
}