package com.guido.olx_marketplace.model.firebase

import com.google.firebase.firestore.FirebaseFirestore

fun getPostsForUserFirestore(user: String, onSuccess: (List<Map<String, Any>>) -> Unit, onFailure: (Exception) -> Unit) {
    val firestoreDB = FirebaseFirestore.getInstance()
    firestoreDB.collection("post")
        .whereEqualTo("usuario", user)
        .get()
        .addOnSuccessListener { querySnapshot ->
            val posts = mutableListOf<Map<String, Any>>()
            for (document in querySnapshot.documents) {
                val post = document.data
                if (post != null) {
                    posts.add(post)
                }
            }
            onSuccess(posts)
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}