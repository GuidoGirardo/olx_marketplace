package com.guido.olx_marketplace.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.guido.olx_marketplace.model.firebase.deletePost
import com.guido.olx_marketplace.model.firebase.newPostFirebase
import com.guido.olx_marketplace.ui.ui.theme.background
import com.guido.olx_marketplace.ui.ui.theme.backgroundItem
import com.guido.olx_marketplace.ui.ui.theme.backgroundNew
import com.guido.olx_marketplace.ui.viewmodel.AppViewModel
import java.util.UUID

@Composable
fun ProfileScreen(navController: NavController, viewModel: AppViewModel) {
    var showPostForm by remember { mutableStateOf(false) }
    val user = viewModel.currentUser.value ?: "Usuario desconocido"
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val storage = Firebase.storage
    val storageRef = storage.reference
    val imageName = "${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
    val imagesRef = storageRef.child("images").child(imageName)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getPostsForUser(user)
    }

    Column(
        modifier = Modifier.fillMaxSize().background(background).padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showPostForm = !showPostForm }, modifier = Modifier.padding(start = 10.dp)) {
            Text(if (showPostForm) "Hide Post Form" else "New Post")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (showPostForm) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().background(backgroundNew).padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Title")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Description")
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        launcher.launch("image/*")
                    }) {
                        Text("Select Image")
                    }
                    Button(onClick = {
                        imageUri?.let { uri ->
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val byteArray = inputStream?.readBytes()
                            val uploadTask = imagesRef.putBytes(byteArray!!)

                            uploadTask.addOnSuccessListener { taskSnapshot ->
                                // La imagen se subió correctamente
                                // Ahora obtén la URL de descarga
                                imagesRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                    val imageUrl = downloadUri.toString()
                                    Log.i("xd", "URL de la imagen: $imageUrl")
                                    newPostFirebase(user, titulo, descripcion, imageUrl)
                                    viewModel.getPostsForUser(user)
                                }.addOnFailureListener {
                                    // Ocurrió un error al obtener la URL de descarga
                                    Log.e("xd", "Error al obtener la URL de la imagen: $it")
                                }
                            }.addOnFailureListener { exception ->
                                // Ocurrió un error al subir la imagen
                                Log.e("xd", "Error al subir la imagen: $exception")
                            }
                        }
                    }) {
                        Text("Send Post")
                    }

                    imageUri?.let { uri ->
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        LazyColumn {
            item {
                viewModel.postUserList.observeAsState().value?.let { posts ->
                    posts.forEach { post ->
                        val titulo = post["titulo"] as String
                        val descripcion = post["descripcion"] as String
                        val url = post["url"] as String
                        Row(
                            modifier = Modifier
                                .background(backgroundItem, RoundedCornerShape(10.dp))
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Imagen a la izquierda
                            Image(
                                painter = rememberImagePainter(url),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(4.dp)), // Redondear las esquinas de la imagen
                                alignment = Alignment.CenterStart
                            )

                            // Datos a la derecha de la imagen
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Título: $titulo")
                                Text("Descripción: $descripcion")
                            }

                            Button(
                                onClick = {
                                    deletePost(
                                        url,
                                        onSuccess = {
                                            viewModel.getPostsForUser(user)
                                        },
                                        onFailure = { exception ->
                                            Log.e("xd", "Error al eliminar post: $exception")
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .widthIn(max = 60.dp)
                                    .height(110.dp)
                                    .padding(start = 10.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("D", fontSize = 9.sp)
                                    Text("E", fontSize = 9.sp)
                                    Text("L", fontSize = 9.sp)
                                    Text("E", fontSize = 9.sp)
                                    Text("T", fontSize = 9.sp)
                                    Text("E", fontSize = 9.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }



    }
}