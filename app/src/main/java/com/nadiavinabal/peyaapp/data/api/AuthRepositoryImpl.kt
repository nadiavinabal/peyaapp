package com.nadiavinabal.peyaapp.data.api

import com.nadiavinabal.peyaapp.data.api.dto.LoginRequest
import com.nadiavinabal.peyaapp.domain.repository.AuthRepository
import org.json.JSONObject
import javax.inject.Inject
import com.nadiavinabal.peyaapp.data.api.dto.LoginResponse
import com.nadiavinabal.peyaapp.data.api.dto.RegisterRequest

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val error = response.errorBody()?.string()
                val message = JSONObject(error ?: "{}").optString("message", "Error desconocido")
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(request: RegisterRequest): Result<String> {
        return try {
            val response = apiService.register(request)
            if (response.isSuccessful) {
                Result.success(response.body()?.message ?: "Registro exitoso")
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}