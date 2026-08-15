package dev.anticlub.videoplayer.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.anticlub.videoplayer.domain.models.AuthErrorType
import dev.anticlub.videoplayer.domain.models.DomainException
import dev.anticlub.videoplayer.domain.usecases.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()){
                _uiState.value = AuthUiState.Error(AuthErrorType.EMPTY_FIELDS)
            } else {
                _uiState.value = AuthUiState.Loading
                runCatching {
                    loginUseCase(email, password)
                }.onSuccess {
                    _uiState.value = AuthUiState.Success
                }.onFailure { error ->
                    when (error){
                        is DomainException.AuthException -> _uiState.value = AuthUiState.Error(error.authErrorType)
                        else -> _uiState.value = AuthUiState.Error(AuthErrorType.UNKNOWN)
                    }
                }
            }

        }
    }
}