package com.guido.olx_marketplace.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController) {
    val title = navController.currentBackStackEntry?.arguments?.getString("title") ?: ""
    val user = navController.currentBackStackEntry?.arguments?.getString("user") ?: ""
    val description = navController.currentBackStackEntry?.arguments?.getString("description") ?: ""

    Column(modifier = Modifier.fillMaxSize()){
        Text("Usuario: $user")
        Text("Título: $title")
        Text("Descripción: $description")

    }
}