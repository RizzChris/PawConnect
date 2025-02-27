package com.example.pawconnect.repository
//import android.util.Log
//import com.google.firebase.firestore.FirebaseFirestore
//
//
//// Modelo de datos unificado con todos los campos necesarios
//data class PetData(
//    val id: String = "",
//    val petName: String = "",
//    val petSpecies: String = "",
//    val petBreed: String = "",
//    val petSize: String = "",
//    val petWeight: String = "",
//    val petAge: String = "",
//    val petSex: String = "",
//    val petPhoto: String = "",
//    val petHistory: String = "",
//    val isSterilized: Boolean = false,
//    val hasVaccines: Boolean = false,
//    val personality: String = "",
//    val medicalCondition: String = "",
//    val getAlongOtherAnimals: String = "",
//    val getAlongKids: String = "",
//    val refugioId: String = ""  // Campo para identificar al refugio que registró la mascota
//)
//
///**
// * Recupera todas las mascotas de la colección global "mascotas".
// */
//fun fetchAllPets(onComplete: (Boolean, List<PetData>, String?) -> Unit) {
//    val db = FirebaseFirestore.getInstance()
//    db.collection("mascotas")
//        .get()
//        .addOnSuccessListener { querySnapshot ->
//            val pets = querySnapshot.documents.mapNotNull { doc ->
//                doc.toObject(PetData::class.java)?.copy(id = doc.id)
//            }
//            onComplete(true, pets, null)
//        }
//        .addOnFailureListener { e ->
//            onComplete(false, emptyList(), e.localizedMessage)
//        }
//}
//
///**
// * Recupera mascotas filtradas por especie desde la colección global "mascotas".
// * Ejemplo: "perro" o "gato" en el campo "petSpecies".
// */
//fun fetchPetsBySpecies(
//    species: String,
//    onComplete: (Boolean, List<PetData>, String?) -> Unit
//) {
//    FirebaseFirestore.getInstance()
//        .collection("mascotas")
//        .whereEqualTo("petSpecies", species)
//        .get()
//        .addOnSuccessListener { querySnapshot ->
//            val pets = querySnapshot.documents.mapNotNull { doc ->
//                doc.toObject(PetData::class.java)?.copy(id = doc.id)
//            }
//            onComplete(true, pets, null)
//        }
//        .addOnFailureListener { e ->
//            onComplete(false, emptyList(), e.localizedMessage)
//        }
//}

