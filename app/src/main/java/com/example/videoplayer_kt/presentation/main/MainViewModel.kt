package com.example.videoplayer_kt.presentation.main

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor()  : ViewModel(){
    private val _isPlayerVisible = MutableStateFlow(false)
    val isPlayerVisible: StateFlow<Boolean> = _isPlayerVisible.asStateFlow()
    private val _pipEvent = MutableSharedFlow<Unit>()
    val pipEvent = _pipEvent.asSharedFlow()

    fun onFragmentChanged(isPlayer: Boolean) {
        _isPlayerVisible.value = isPlayer
    }

    fun isCorrectSdk(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    fun shouldEnterPip() {
        if (_isPlayerVisible.value && isCorrectSdk()){
            viewModelScope.launch {
                _pipEvent.emit(Unit)
            }
        }
    }


}