package dev.anticlub.videoplayer.domain.usecases.auth

import dev.anticlub.videoplayer.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}