package com.guido.olx_marketplace.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.guido.olx_marketplace.ui.navigation.AppScreens
import com.guido.olx_marketplace.ui.viewmodel.AppViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: AppViewModel) {
    val user = viewModel.currentUser.value ?: "Usuario desconocido"
    val postList by viewModel.postList.observeAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Lista de Posts:")
            Text("hola $user")
            LazyColumn {
                items(postList) { post ->
                    val title = post["titulo"].toString()
                    val user = post["usuario"].toString()
                    val description = post["descripcion"].toString()
                    PostItem(
                        title = title,
                        user = user,
                        description = description,
                        onClick = { title, user, description ->
                            navController.navigate(
                                "detail/${title}/${user}/${description}"
                            )
                        }
                    )
                }
            }
        }
        Button(
            onClick = {
                navController.navigate(AppScreens.ProfileScreen.route)
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text("Profile")
        }
    }
}

@Composable
fun PostItem(
    title: String,
    user: String,
    description: String,
    onClick: (String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = { onClick(title, user, description) })
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Usuario: $user")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Título: $title")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descripción: $description")
        }
    }
}
