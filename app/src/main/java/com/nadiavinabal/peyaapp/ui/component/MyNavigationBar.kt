package com.nadiavinabal.peyaapp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun MyNavigationBar(
    navController: NavController,
    currentDestination: NavBackStackEntry?,
    modifier: Modifier = Modifier
) {
    NavigationBar(containerColor = Color(0xFFFCF8F8)) {
        NavigationBarItem(
            selected = currentDestination?.destination?.route == "shop",
            onClick = {
                if (currentDestination?.destination?.route != "shop") {
                    navController.navigate("shop") {
                        popUpTo("shop") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color(0xFF974E52))
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Próximamente */ },
            icon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF974E52))
            }
        )
        NavigationBarItem(
            selected = currentDestination?.destination?.route == "shoppingCart",
            onClick = {
                if (currentDestination?.destination?.route != "shoppingCart") {
                    navController.navigate("shoppingCart") {
                        popUpTo("shop")
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color(0xFF1B0E0F))
            }
        )
        NavigationBarItem(
            selected = currentDestination?.destination?.route == "profile",
            onClick = {
                if (currentDestination?.destination?.route != "profile") {
                    navController.navigate("profile") {
                        popUpTo("shop")
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color(0xFF974E52))
            }
        )
        NavigationBarItem(
            selected = currentDestination?.destination?.route == "orderHistory",
            onClick = {
                if (currentDestination?.destination?.route != "orderHistory") {
                    navController.navigate("orderHistory") {
                        popUpTo("shop")
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(Icons.Default.List, contentDescription = "Historial", tint = Color(0xFF974E52))
            }
        )

    }
}