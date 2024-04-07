package com.guido.olx_marketplace.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.guido.olx_marketplace.model.firebase.newPostFirebase
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
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showPostForm = !showPostForm }) {
            Text(if (showPostForm) "Hide Post Form" else "New Post")
        }

        if (showPostForm) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
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
                    Button(onClick = {
                        launcher.launch("image/*")
                    }) {
                        Text("Select Image")
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


        // mostrar lista de videos del usuario
        // hacer un recycler view que llame a una función de otro archivo
        // esa funcion se encargará de devolver todos los post del usuario "user"
        LazyColumn {
            item {
                viewModel.postUserList.observeAsState().value?.let { posts ->
                    Column {
                        posts.forEach { post ->
                            val titulo = post["titulo"] as String
                            val descripcion = post["descripcion"] as String
                            val usuario = post["usuario"] as String
                            val url = post["url"] as String
                            Log.i("xd", usuario)

                            // Aquí muestra los detalles del post en tu UI
                            Text("Título: $titulo")
                            Text("Descripción: $descripcion")
                            Text("Usuario: $usuario")
                            Image(
                                painter = rememberImagePainter(url),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }



    }
}