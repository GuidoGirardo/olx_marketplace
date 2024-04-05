package com.guido.olx_marketplace.navigation

sealed class AppScreens(val route: String) {

    object RegisterScreen : AppScreens("register_screen")
    object LoginScreen : AppScreens("login_screen")

}