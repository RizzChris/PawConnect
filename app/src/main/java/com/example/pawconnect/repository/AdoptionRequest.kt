package com.example.pawconnect.repository

data class AdoptionRequest(
    val requestId: String = "",        // ID del documento en Firestore
    val userName: String = "",
    val userSurname: String = "",
    val userAge: String = "",
    val userEmail: String = "",
    val userPhone: String = "",
    val reason: String = "",           // Motivo de adopción
    // Datos del entorno
    val currentlyHasPets: Boolean = false,
    val previouslyHadPets: Boolean = false,
    val peopleInHouse: String = "",
    val allAgree: Boolean = false,
    val hasKids: Boolean = false,
    val ownHouse: Boolean = false,
    val landlordAllows: Boolean = false,
    // Estado de la solicitud, p. ej. "pendiente", "aprobada", "rechazada"
    val status: String = "pendiente",
    // Fecha/hora de la solicitud, o petId, etc. si se requiere
    val petId: String = ""             // Si deseas vincular la solicitud a una mascota específica
)
