package com.example.pawconnect.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Modelo de datos unificado con todos los campos necesarios
data class PetData(
    val id: String = "",
    val petName: String = "",
    val petSpecies: String = "",
    val petBreed: String = "",
    val petSize: String = "",
    val petWeight: String = "",
    val petAge: String = "",
    val petSex: String = "",
    val petPhoto: String = "",
    val petHistory: String = "",
    val isSterilized: Boolean = false,
    val hasVaccines: Boolean = false,
    val personality: String = "",
    val medicalCondition: String = "",
    val getAlongOtherAnimals: String = "",
    val getAlongKids: String = "",
    val refugioId: String = ""  // Identifica al refugio que registró la mascota
)

/**
 * Función para registrar una mascota en Firestore usando una colección global "mascotas".
 */
fun registerPet(
    petName: String,
    petSpecies: String,
    petBreed: String,
    petSize: String,
    petWeight: String,
    petAge: String,
    petSex: String,
    petPhoto: String,
    petHistory: String,
    isSterilized: Boolean,
    hasVaccines: Boolean,
    personality: String,
    medicalCondition: String,
    getAlongOtherAnimals: String,
    getAlongKids: String,
    onComplete: (Boolean, String?) -> Unit
) {
    // Obtiene el UID del refugio autenticado
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId == null) {
        onComplete(false, "Usuario no autenticado")
        return
    }

    // Crea el mapa de datos
    val petData = hashMapOf(
        "petName" to petName,
        "petSpecies" to petSpecies,
        "petBreed" to petBreed,
        "petSize" to petSize,
        "petWeight" to petWeight,
        "petAge" to petAge,
        "petSex" to petSex,
        "petPhoto" to petPhoto,
        "petHistory" to petHistory,
        "isSterilized" to isSterilized,
        "hasVaccines" to hasVaccines,
        "personality" to personality,
        "medicalCondition" to medicalCondition,
        "getAlongOtherAnimals" to getAlongOtherAnimals,
        "getAlongKids" to getAlongKids,
        "refugioId" to userId
    )

    // Guarda en la colección global "mascotas"
    FirebaseFirestore.getInstance()
        .collection("mascotas")
        .add(petData)
        .addOnSuccessListener { onComplete(true, null) }
        .addOnFailureListener { e -> onComplete(false, e.message) }
}

/**
 * Recupera todas las mascotas de la colección global "mascotas".
 */
fun fetchAllPets(onComplete: (Boolean, List<PetData>, String?) -> Unit) {
    FirebaseFirestore.getInstance()
        .collection("mascotas")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val pets = querySnapshot.documents.mapNotNull { doc ->
                doc.toObject(PetData::class.java)?.copy(id = doc.id)
            }
            onComplete(true, pets, null)
        }
        .addOnFailureListener { e ->
            onComplete(false, emptyList(), e.localizedMessage)
        }
}

/**
 * Recupera mascotas filtradas por especie desde la colección global "mascotas".
 * Ej.: species = "perro" o "gato"
 */
fun fetchPetsBySpecies(
    species: String,
    onComplete: (Boolean, List<PetData>, String?) -> Unit
){
    FirebaseFirestore.getInstance()
        .collection("mascotas")
        .whereEqualTo("petSpecies", species)
        .get()
        .addOnSuccessListener { querySnapshot ->
            val pets = querySnapshot.documents.mapNotNull { doc ->
                doc.toObject(PetData::class.java)?.copy(id = doc.id)
            }
            onComplete(true, pets, null)
        }
        .addOnFailureListener { e ->
            onComplete(false, emptyList(), e.localizedMessage)
        }
}



