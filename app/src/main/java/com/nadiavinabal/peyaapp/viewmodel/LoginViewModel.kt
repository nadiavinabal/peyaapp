package com.nadiavinabal.peyaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadiavinabal.peyaapp.domain.repository.AuthRepository
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
): ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val emailError: String? = null,
        val passwordError: String? = null,
        val isLoginEnabled: Boolean = false,
        val loading: Boolean = false,
        val dialogMessage: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.update {
            val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
            it.copy(
                email = value,
                emailError = if (isValid || value.isBlank()) null else "Email no válido"
            ).validate()
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                passwordError = if (value.length >= 8 || value.isBlank()) null else "Mínimo 8 caracteres"
            ).validate()
        }
    }

    fun login(onSuccess: () -> Unit) {
        val email = _uiState.value.email
        val password = _uiState.value.password

        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = { response ->
                    if (response.message == "Login exitoso") {
                        onSuccess()
                    } else {
                        _uiState.update { it.copy(dialogMessage = response.message) }
                    }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(dialogMessage = error.message ?: "Error inesperado") }
                }
            )
            _uiState.update { it.copy(loading = false) }
        }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(dialogMessage = null) }
    }

    private fun UiState.validate(): UiState {
        val isEmailValid = emailError == null && email.isNotBlank()
        val isPasswordValid = passwordError == null && password.isNotBlank()
        return copy(isLoginEnabled = isEmailValid && isPasswordValid)
    }
}
