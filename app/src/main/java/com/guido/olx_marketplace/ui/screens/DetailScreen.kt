package com.guido.olx_marketplace.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController) {
    val title = navController.currentBackStackEntry?.arguments?.getString("title") ?: ""
    val user = navController.currentBackStackEntry?.arguments?.getString("user") ?: ""
    val description = navController.currentBackStackEntry?.arguments?.getString("description") ?: ""



    Column(modifier = Modifier.fillMaxSize()){
        Text("Título: $title")
        Text("Usuario: $user")
        Text("Descripción: $description")
    }
}