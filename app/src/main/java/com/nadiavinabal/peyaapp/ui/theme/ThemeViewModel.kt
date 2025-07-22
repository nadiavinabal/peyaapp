package com.nadiavinabal.peyaapp.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel : ViewModel() {
    private val _useDarkTheme = MutableStateFlow(false)
    val useDarkTheme: StateFlow<Boolean> = _useDarkTheme.asStateFlow()

    fun toggleTheme() {
        _useDarkTheme.value = !_useDarkTheme.value
    }
}