package com.nadiavinabal.peyaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nadiavinabal.peyaapp.data.Product
import com.nadiavinabal.peyaapp.navigation.NavigationWrapper
import com.nadiavinabal.peyaapp.ui.screen.LoginScreen
import com.nadiavinabal.peyaapp.ui.screen.ShopScreen
import com.nadiavinabal.peyaapp.ui.theme.PeyaappTheme
import com.nadiavinabal.peyaapp.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.ReadOnlyProperty
import androidx.compose.runtime.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.useDarkTheme.collectAsState()
            PeyaappTheme(darkTheme = isDarkTheme) {
              NavigationWrapper(themeViewModel)

            }
        }
    }
}
