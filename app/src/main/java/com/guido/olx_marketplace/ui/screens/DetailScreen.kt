package com.guido.olx_marketplace.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.guido.olx_marketplace.ui.ui.theme.background
import com.guido.olx_marketplace.ui.ui.theme.negro
import java.net.URLDecoder

@Composable
fun DetailScreen(navController: NavController) {
    val title = navController.currentBackStackEntry?.arguments?.getString("title") ?: ""
    val user = navController.currentBackStackEntry?.arguments?.getString("user") ?: ""
    val description = navController.currentBackStackEntry?.arguments?.getString("description") ?: ""
    val url = navController.currentBackStackEntry?.arguments?.getString("url") ?: ""
    val decodedUrl = URLDecoder.decode(url, "UTF-8")

    Log.i("xd", "url en la detail $url")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(vertical = 16.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(url),
                contentDescription = null,
                modifier = Modifier
                    .width(190.dp)
                    .height(280.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(){
            Text(text = "User",
                modifier = Modifier.background(negro, RoundedCornerShape(5.dp))
                    .padding(vertical = 3.dp, horizontal = 5.dp),
                color = background)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "$user")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(){
            Text(text = "Title",
                modifier = Modifier.background(negro, RoundedCornerShape(5.dp))
                    .padding(vertical = 3.dp, horizontal = 5.dp),
                color = background)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "$title")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(){
            Text(text = "Description",
                modifier = Modifier.background(negro, RoundedCornerShape(5.dp))
                    .padding(vertical = 3.dp, horizontal = 5.dp),
                color = background)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "$description")
        }
    }
}