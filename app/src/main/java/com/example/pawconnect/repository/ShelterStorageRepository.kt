package com.example.pawconnect.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

fun uploadImageToStorage(imageUri: Uri, onComplete: (String?) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference
    // Por ejemplo, almacena la imagen en "mascotas/{nombre_imagen}"
    val imageRef = storageRef.child("mascotas/${imageUri.lastPathSegment}")

    imageRef.putFile(imageUri)
        .addOnSuccessListener {
            // Obtener URL de descarga
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onComplete(uri.toString())
            }
        }
        .addOnFailureListener {
            onComplete(null)
        }
}
