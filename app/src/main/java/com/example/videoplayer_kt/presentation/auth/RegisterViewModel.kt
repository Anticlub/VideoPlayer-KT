package com.example.videoplayer_kt.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer_kt.domain.models.AuthResult
import com.example.videoplayer_kt.domain.usecases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val  registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            when (val result = registerUseCase(email, password)) {
                is AuthResult.Success -> _uiState.value = AuthUiState.Success
                is AuthResult.Error -> _uiState.value = AuthUiState.Error(result.type)
            }
        }
    }
}