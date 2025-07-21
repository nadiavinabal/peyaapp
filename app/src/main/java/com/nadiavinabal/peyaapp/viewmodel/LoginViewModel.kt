package com.nadiavinabal.peyaapp.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadiavinabal.peyaapp.domain.repository.AuthRepository

import com.nadiavinabal.peyaapp.ui.screen.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(value: String) {
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value)
    }



    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.login(uiState.email, uiState.password)
            result
                .onSuccess { response ->
                    uiState = uiState.copy(
                        // message = response.message,
                        user = response.user,
                        isLoading = false
                    )
                    onSuccess()
                }
                .onFailure { error ->
                    uiState = uiState.copy(isLoading = false)
                    onError(error.message ?: "Error desconocido")
                }
        }
    }
}
