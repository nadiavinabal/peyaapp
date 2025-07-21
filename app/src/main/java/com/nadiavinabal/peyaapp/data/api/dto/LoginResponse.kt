package com.nadiavinabal.peyaapp.data.api.dto

import com.nadiavinabal.peyaapp.model.User

data class LoginResponse(
    val message: String,
    val user: User? = null
)