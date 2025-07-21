package com.nadiavinabal.peyaapp.domain.repository

import com.nadiavinabal.peyaapp.data.api.dto.LoginResponse
import com.nadiavinabal.peyaapp.data.api.dto.RegisterRequest

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
    suspend fun register(request: RegisterRequest): Result<String>
}