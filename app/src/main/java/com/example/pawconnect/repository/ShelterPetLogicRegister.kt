package com.example.pawconnect.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


// Modelo de datos (opcional, para estructurar la información de la mascota)
data class Pet(
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
    val getAlongKids: String = ""
)

/**
 * Función para registrar una mascota en Firestore.
 *
 * @param petName Nombre de la mascota.
 * @param petSpecies Especie de la mascota (Perro o gato).
 * @param petBreed Raza de la mascota.
 * @param petSize Tamaño de la mascota.
 * @param petWeight Peso de la mascota.
 * @param petAge Edad aproximada.
 * @param petSex Sexo de la mascota.
 * @param petPhoto URL o referencia a la fotografía de la mascota.
 * @param petHistory Historia de la mascota.
 * @param isSterilized Indica si está esterilizada.
 * @param hasVaccines Indica si tiene vacunas al día.
 * @param personality Descripción de su personalidad.
 * @param medicalCondition Información sobre condiciones médicas o necesidades especiales.
 * @param getAlongOtherAnimals ¿Se lleva bien con otros animales?
 * @param getAlongKids ¿Se lleva bien con niños?
 * @param onComplete Callback que retorna (Boolean, String?) indicando éxito y, en caso de error, el mensaje.
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
    // Obtiene el UID del usuario autenticado (refugio)
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId == null) {
        onComplete(false, "Usuario no autenticado")
        return
    }

    // Crea un mapa con los datos de la mascota
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
        "getAlongKids" to getAlongKids
    )

    // Guarda los datos en Firestore
    FirebaseFirestore.getInstance()
        .collection("users")
        .document(userId)
        .collection("mascotas")
        .add(petData)
        .addOnSuccessListener {
            onComplete(true, null)
        }
        .addOnFailureListener { e ->
            onComplete(false, e.message)
        }
}

