package com.example.pawconnect.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore


object FavoritesRepository {

    /**
     * Agrega la mascota con ID [petId] a la subcolección "favoritos" del usuario autenticado.
     */
    fun addFavorite(petId: String, onComplete: (Boolean, String?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onComplete(false, "Usuario no autenticado")
            return
        }
        // Datos a guardar: solo la referencia a la mascota y un timestamp.
        val favoriteData = mapOf(
            "petId" to petId,
            "timestamp" to System.currentTimeMillis()
        )
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favoritos")
            .add(favoriteData)
            .addOnSuccessListener { onComplete(true, null) }
            .addOnFailureListener { e ->
                onComplete(false, e.localizedMessage)
            }
    }

    /**
     * Hace una consulta única a la subcolección "favoritos" para obtener los petIds,
     * luego consulta la colección global "mascotas" para recuperar la información completa de cada mascota.
     *
     * Nota: Firestore limita whereIn a 10 elementos. Si el usuario tiene más de 10 favoritos,
     * tendrás que paginar o realizar varias consultas.
     */
    fun fetchFavorites(onComplete: (Boolean, List<PetData>, String?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onComplete(false, emptyList(), "Usuario no autenticado")
            return
        }
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .collection("favoritos")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val petIds = querySnapshot.documents.mapNotNull { it.getString("petId") }
                if (petIds.isEmpty()) {
                    onComplete(true, emptyList(), null)
                    return@addOnSuccessListener
                }
                db.collection("mascotas")
                    .whereIn(FieldPath.documentId(), petIds)
                    .get()
                    .addOnSuccessListener { petsSnapshot ->
                        val pets = petsSnapshot.documents.mapNotNull { doc ->
                            doc.toObject(PetData::class.java)?.copy(id = doc.id)
                        }
                        onComplete(true, pets, null)
                    }
                    .addOnFailureListener { e2 ->
                        onComplete(false, emptyList(), e2.localizedMessage)
                    }
            }
            .addOnFailureListener { e ->
                onComplete(false, emptyList(), e.localizedMessage)
            }
    }

    /**
     * Escucha en tiempo real los cambios en la subcolección "favoritos" del usuario autenticado.
     * Cada vez que cambie, consulta la colección global "mascotas" para obtener la información completa.
     * Ideal si quieres que la pantalla de favoritos se actualice inmediatamente cuando se agregue o elimine un favorito.
     */
    fun listenToFavorites(
        onUpdate: (List<PetData>) -> Unit,
        onError: (String?) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onError("Usuario no autenticado")
            return
        }
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .collection("favoritos")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error.localizedMessage)
                    return@addSnapshotListener
                }
                val petIds = snapshot?.documents?.mapNotNull { it.getString("petId") } ?: emptyList()
                if (petIds.isEmpty()) {
                    onUpdate(emptyList())
                } else {
                    db.collection("mascotas")
                        .whereIn(FieldPath.documentId(), petIds)
                        .get()
                        .addOnSuccessListener { petsSnapshot ->
                            val pets = petsSnapshot.documents.mapNotNull { doc ->
                                doc.toObject(PetData::class.java)?.copy(id = doc.id)
                            }
                            onUpdate(pets)
                        }
                        .addOnFailureListener { e2 ->
                            onError(e2.localizedMessage)
                        }
                }
            }
    }

    /**
     * Elimina el favorito correspondiente a la mascota con ID [petId] de la subcolección "favoritos" del usuario autenticado.
     */
    fun removeFavorite(petId: String, onComplete: (Boolean, String?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onComplete(false, "Usuario no autenticado")
            return
        }
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .collection("favoritos")
            .whereEqualTo("petId", petId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val batch = db.batch()
                querySnapshot.documents.forEach { doc ->
                    batch.delete(doc.reference)
                }
                batch.commit()
                    .addOnSuccessListener { onComplete(true, null) }
                    .addOnFailureListener { e -> onComplete(false, e.localizedMessage) }
            }
            .addOnFailureListener { e ->
                onComplete(false, e.localizedMessage)
            }
    }

    /**
     * Verifica si la mascota con [petId] ya está en la subcolección "favoritos" del usuario.
     */
    fun isFavorite(petId: String, onComplete: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onComplete(false)
            return
        }
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favoritos")
            .whereEqualTo("petId", petId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                onComplete(!querySnapshot.isEmpty)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
}



