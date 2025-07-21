package com.nadiavinabal.peyaapp.data.api.dto

data class LoginRequest(
    val email: String,
    val encryptedPassword: String
)