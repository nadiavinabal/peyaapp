package com.nadiavinabal.peyaapp.data.api

import com.nadiavinabal.peyaapp.data.api.dto.LoginRequest
import com.nadiavinabal.peyaapp.data.api.dto.LoginResponse
import com.nadiavinabal.peyaapp.data.api.dto.RegisterRequest
import com.nadiavinabal.peyaapp.data.api.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}