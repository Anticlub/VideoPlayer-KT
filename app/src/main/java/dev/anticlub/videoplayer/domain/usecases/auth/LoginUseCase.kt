package dev.anticlub.videoplayer.domain.usecases.auth

import dev.anticlub.videoplayer.domain.models.User
import dev.anticlub.videoplayer.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): User {
        return repository.login(email, password)
    }
}