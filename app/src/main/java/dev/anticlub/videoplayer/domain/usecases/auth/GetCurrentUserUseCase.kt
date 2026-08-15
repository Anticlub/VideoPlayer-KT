package dev.anticlub.videoplayer.domain.usecases.auth

import dev.anticlub.videoplayer.domain.models.User
import dev.anticlub.videoplayer.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke() : User? {
        return repository.getCurrentUser()
    }
}