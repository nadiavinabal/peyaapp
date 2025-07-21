package com.nadiavinabal.peyaapp.data.api.dto

data class RegisterRequest(
    val email: String,
    val fullName: String,
    val encryptedPassword: String
)