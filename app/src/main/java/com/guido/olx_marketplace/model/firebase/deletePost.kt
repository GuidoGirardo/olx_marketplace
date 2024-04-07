package com.guido.olx_marketplace.model.firebase

import com.google.firebase.firestore.FirebaseFirestore

fun deletePost(url: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("post")

    postCollection
        .whereEqualTo("url", url)
        .get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                // Obtener la referencia del documento y eliminarlo
                postCollection.document(document.id).delete()
                    .addOnSuccessListener {
                        println("Documento eliminado exitosamente")
                        onSuccess() // Llama a la función onSuccess después de eliminar el post
                    }
                    .addOnFailureListener { e ->
                        println("Error al eliminar documento: $e")
                        onFailure(e) // Llama a la función onFailure en caso de error
                    }
            }
        }
        .addOnFailureListener { e ->
            println("Error al realizar la consulta: $e")
            onFailure(e) // Llama a la función onFailure en caso de error
        }
}
