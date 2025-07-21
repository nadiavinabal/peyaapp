package com.nadiavinabal.peyaapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadiavinabal.peyaapp.domain.repository.AuthRepository
import com.nadiavinabal.peyaapp.model.User
import com.nadiavinabal.peyaapp.viewmodel.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.mock
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.whenever
import com.nadiavinabal.peyaapp.data.api.dto.LoginResponse
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        authRepository = mock()
        viewModel = LoginViewModel(authRepository)
    }

    @Test
    fun `onEmailChange updates uiState email`() {
        val email = "test@example.com"
        viewModel.onEmailChange(email)
        assertEquals(email, viewModel.uiState.email)
    }

    @Test
    fun `onPasswordChange updates uiState password`() {
        val password = "12345678"
        viewModel.onPasswordChange(password)
        assertEquals(password, viewModel.uiState.password)
    }

    @Test
    fun `login success updates uiState user and calls onSuccess`() = runTest {
        val email = "test@example.com"
        val password = "12345678"
        val user = User(
            id = "1",
            email = "test@example.com",
            fullName = "Maria Lopez",
            createdAt = "2025-07-21",
            updatedAt = "2025-07-21"
        ) // Reemplazá por tu modelo real

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        whenever(authRepository.login(email, password))
            .thenReturn(Result.success(LoginResponse(message = "Login exitoso", user = user)))

        var successCalled = false
        viewModel.login(onSuccess = { successCalled = true }, onError = {})

        advanceUntilIdle()
        println("🔍 Resultado de uiState.user: ${viewModel.uiState.user}")
        assertEquals(user, viewModel.uiState.user)
        assertFalse(viewModel.uiState.isLoading)
        assertTrue(successCalled)
    }

    @Test
    fun `login failure updates isLoading and calls onError`() = runTest {
        val email = "test@example.com"
        val password = "wrongpass"
        val errorMessage = "Credenciales inválidas"

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        whenever(authRepository.login(email, password)).thenReturn(Result.failure(Exception(errorMessage)))

        var capturedError: String? = null
        viewModel.login(onSuccess = {}, onError = { capturedError = it })

        advanceUntilIdle()

        assertFalse(viewModel.uiState.isLoading)
        assertEquals(errorMessage, capturedError)
    }
}