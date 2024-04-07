package com.guido.olx_marketplace.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.net.URLDecoder

@Composable
fun DetailScreen(navController: NavController) {
    val title = navController.currentBackStackEntry?.arguments?.getString("title") ?: ""
    val user = navController.currentBackStackEntry?.arguments?.getString("user") ?: ""
    val description = navController.currentBackStackEntry?.arguments?.getString("description") ?: ""
    val url = navController.currentBackStackEntry?.arguments?.getString("url") ?: ""
    val decodedUrl = URLDecoder.decode(url, "UTF-8")

    Log.i("xd", "url en la detail $url")

    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberImagePainter(url),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text("Usuario: $user")
        Text("Título: $title")
        Text("Descripción: $description")
    }
}