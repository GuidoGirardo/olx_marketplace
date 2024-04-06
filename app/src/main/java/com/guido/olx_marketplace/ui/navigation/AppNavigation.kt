package com.guido.olx_marketplace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guido.olx_marketplace.ui.screens.HomeScreen
import com.guido.olx_marketplace.ui.screens.LoginScreen
import com.guido.olx_marketplace.ui.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.RegisterScreen.route) {

        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }

    }

}