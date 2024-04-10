package com.guido.olx_marketplace.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.guido.olx_marketplace.ui.navigation.AppScreens
import com.guido.olx_marketplace.ui.ui.theme.background
import com.guido.olx_marketplace.ui.ui.theme.backgroundItem
import com.guido.olx_marketplace.ui.ui.theme.negro
import com.guido.olx_marketplace.ui.viewmodel.AppViewModel
import java.net.URLEncoder

@Composable
fun HomeScreen(navController: NavController, viewModel: AppViewModel) {
    val user = viewModel.currentUser.value ?: "Usuario desconocido"
    val postList by viewModel.postList.observeAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize().background(background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Welcome $user!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text("Products:", Modifier.padding(start = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(postList) { post ->
                    val title = post["titulo"].toString()
                    val user = post["usuario"].toString()
                    val description = post["descripcion"].toString()
                    val urlHome = post["url"].toString()
                    val encodedUrl = URLEncoder.encode(urlHome, "UTF-8")
                    Log.i("xd", "url en home $urlHome")
                    PostItem(
                        title = title,
                        user = user,
                        description = description,
                        urlHome = urlHome,
                        onClick = { title, user, description, urlHome ->
                            navController.navigate(
                                "detail?title=$title&user=$user&description=$description&url=$encodedUrl"
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
    urlHome: String,
    onClick: (String, String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = { onClick(title, user, description, urlHome) })
    ) {
        Row(modifier = Modifier.fillMaxSize().background(backgroundItem).padding(12.dp)) {
            Image(
                painter = rememberImagePainter(urlHome),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(115.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(){
                    Text(text = "User",
                        modifier = Modifier.background(negro, RoundedCornerShape(5.dp))
                            .padding(vertical = 3.dp, horizontal = 5.dp),
                        color = background)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "$user")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(){
                    Text(text = "Title",
                        modifier = Modifier.background(negro, RoundedCornerShape(5.dp))
                            .padding(vertical = 3.dp, horizontal = 5.dp),
                        color = background)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "$title")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(){
                    Text(text = "Desc",
                        modifier = Modifier.background(negro, RoundedCornerShape(5.dp))
                            .padding(vertical = 3.dp, horizontal = 5.dp),
                        color = background)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "$description")
                }
            }
        }
    }
}