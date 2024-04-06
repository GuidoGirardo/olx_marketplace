package com.guido.olx_marketplace.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.guido.olx_marketplace.ui.viewmodel.AppViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()

    val postList by viewModel.postList.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Lista de Posts:")
        LazyColumn {
            items(postList) { post ->
                PostItem(
                    title = post["usuario"].toString(),
                    user = post["titulo"].toString(),
                    description = post["descripcion"].toString()
                )
            }
        }
    }
}

// post item
@Composable
fun PostItem(title: String, user: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Título: $title")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Usuario: $user")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descripción: $description")
        }
    }
}