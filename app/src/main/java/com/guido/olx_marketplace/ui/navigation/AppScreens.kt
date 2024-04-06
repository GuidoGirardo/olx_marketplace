package com.guido.olx_marketplace.ui.navigation

sealed class AppScreens(val route: String) {
    object RegisterScreen : AppScreens("register_screen")
    object LoginScreen : AppScreens("login_screen")
    object HomeScreen : AppScreens("home_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object DetailScreen : AppScreens("detail/{title}/{user}/{description}")
}