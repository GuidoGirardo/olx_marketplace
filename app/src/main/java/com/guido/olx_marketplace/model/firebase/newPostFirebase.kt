package com.guido.olx_marketplace.model.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

fun newPostFirebase(user: String, title: String, description: String, imageUrl: String){
    val db = FirebaseFirestore.getInstance()

    val newPost = hashMapOf(
        "usuario" to user,
        "titulo" to title,
        "descripcion" to description,
        "url" to imageUrl
    )

    db.collection("post")
        .add(newPost)
        .addOnSuccessListener { documentReference ->
            Log.i("xd", "DocumentSnapshot added with ID: ${documentReference.id}")

        }

}