package com.nadiavinabal.peyaapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nadiavinabal.peyaapp.ui.screen.LoginScreen
import com.nadiavinabal.peyaapp.ui.screen.ProfileScreen
import com.nadiavinabal.peyaapp.ui.screen.RegisterScreem
import com.nadiavinabal.peyaapp.ui.screen.ShopScreen
import com.nadiavinabal.peyaapp.ui.screen.ShoppingCartScreen
import com.nadiavinabal.peyaapp.viewmodel.ProfileViewModel
import androidx.compose.runtime.getValue
import com.nadiavinabal.peyaapp.ui.screen.OrderHistoryScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navigationToShop = { navController.navigate("shop") }, navigationToRegister = { navController.navigate("profile") })
        }

        composable("register") {
            RegisterScreem()
        }

        composable("shop") {
            ShopScreen(
                navigationBack = { navController.popBackStack() },
                navigationToShoppingCart = { navController.navigate("shoppingCart") },
                navController = navController,
                currentDestination = currentBackStackEntry
            )
        }

        composable("shoppingCart") {
            ShoppingCartScreen()
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                currentDestination = currentBackStackEntry
            )
        }

        composable("orderHistory") {
            OrderHistoryScreen(
                onBackToProfile = {
                    navController.navigate("profile")
                },
                navigationBack = { navController.popBackStack() }
            )
        }
    }
}