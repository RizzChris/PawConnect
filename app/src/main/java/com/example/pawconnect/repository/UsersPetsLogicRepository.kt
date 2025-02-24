package com.example.pawconnect.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.auth.FirebaseAuth
import android.util.Log

data class PetData(
    val petName: String = "",
    val petAge: String = "",
    val petSex: String = "",
    val petPhoto: String = "",  // URL de la foto
    val petSize: String = "",
    val petBreed: String = ""
    // Agrega más campos si lo deseas
)

fun fetchAllPets(onComplete: (Boolean, List<PetData>, String?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val petsList = mutableListOf<PetData>()

    // Recuperar todos los documentos de "users"
    db.collection("users").get()
        .addOnSuccessListener { usersSnapshot ->
            // Para cada documento (refugio), busca la subcolección "mascotas"
            val userDocuments = usersSnapshot.documents
            if (userDocuments.isEmpty()) {
                onComplete(true, emptyList(), null)
                return@addOnSuccessListener
            }

            var pending = userDocuments.size
            userDocuments.forEach { userDoc ->
                userDoc.reference.collection("mascotas").get()
                    .addOnSuccessListener { mascotasSnapshot ->
                        for (mascotaDoc in mascotasSnapshot.documents) {
                            val pet = mascotaDoc.toObject(PetData::class.java)
                            if (pet != null) {
                                petsList.add(pet)
                            }
                        }
                        pending--
                        if (pending == 0) {
                            // Hemos terminado de iterar en todos los refugios
                            onComplete(true, petsList, null)
                        }
                    }
                    .addOnFailureListener { e ->
                        pending--
                        Log.e("fetchAllPets", "Error al obtener mascotas", e)
                        if (pending == 0) {
                            onComplete(false, emptyList(), e.localizedMessage)
                        }
                    }
            }
        }
        .addOnFailureListener { e ->
            onComplete(false, emptyList(), e.localizedMessage)
        }
}
