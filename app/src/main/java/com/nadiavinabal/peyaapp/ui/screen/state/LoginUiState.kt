package com.nadiavinabal.peyaapp.ui.screen.state

import com.nadiavinabal.peyaapp.model.User

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val message: String = "",
    val user: User? = null,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isFormValid: Boolean = false
)