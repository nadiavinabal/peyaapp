package com.nadiavinabal.peyaapp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun ThemeToggleButton(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    IconButton(onClick = onToggleTheme) {
        Icon(
            imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
            contentDescription = "Cambiar tema"
        )
    }
}