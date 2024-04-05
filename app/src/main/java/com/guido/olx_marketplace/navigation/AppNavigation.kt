package com.guido.olx_marketplace.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guido.olx_marketplace.screens.LoginScreen
import com.guido.olx_marketplace.screens.RegisterScreen

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

    }

}