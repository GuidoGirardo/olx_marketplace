package com.guido.olx_marketplace.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.guido.olx_marketplace.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController){

    Text("hola login", modifier = Modifier.clickable { navController.navigate(AppScreens.RegisterScreen.route) } )

}