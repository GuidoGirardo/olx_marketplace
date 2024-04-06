package com.guido.olx_marketplace.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.guido.olx_marketplace.ui.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController){

    Text("hola login", modifier = Modifier.clickable { navController.navigate(AppScreens.HomeScreen.route) } )

}