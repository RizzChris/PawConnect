package com.example.pawconnect.ui.screens.shelter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pawconnect.R
import com.example.pawconnect.repository.AdoptionRequest
import com.example.pawconnect.repository.AdoptionRequestsRepository
import com.example.pawconnect.repository.PetData
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterRequestDetailScreen(navController: NavController, requestId: String) {
    var request by remember { mutableStateOf<AdoptionRequest?>(null) }
    var pet by remember { mutableStateOf<PetData?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Cargar la solicitud
    LaunchedEffect(requestId) {
        if (requestId.isNotBlank()) {
            // 1) Obtener la solicitud desde "adoptionRequests"
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("adoptionRequests").document(requestId)
            docRef.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val adoptionReq = snapshot.toObject(AdoptionRequest::class.java)
                        if (adoptionReq != null) {
                            request = adoptionReq
                            // Luego obtenemos la mascota
                            loadPet(adoptionReq.petId) { petData, error ->
                                if (petData != null) {
                                    pet = petData
                                } else {
                                    errorMessage = error ?: "Error al cargar la mascota."
                                }
                                loading = false
                            }
                        } else {
                            errorMessage = "No se pudo parsear la solicitud."
                            loading = false
                        }
                    } else {
                        errorMessage = "No se encontró la solicitud."
                        loading = false
                    }
                }
                .addOnFailureListener { e ->
                    errorMessage = e.localizedMessage ?: "Error desconocido"
                    loading = false
                }
        } else {
            errorMessage = "No se proporcionó requestId."
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Revisar Solicitud") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_huella),
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage.isNotEmpty() -> {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                request != null -> {
                    // Muestra los datos
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Muestra la mascota (pet) si se cargó
                        if (pet != null) {
                            // Imagen de la mascota
                            val painter = rememberAsyncImagePainter(
                                model = pet!!.petPhoto,
                                fallback = painterResource(R.drawable.my_placeholder),
                                error = painterResource(R.drawable.my_placeholder)
                            )
                            Image(
                                painter = painter,
                                contentDescription = pet!!.petName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                            // Nombre de la mascota
                            Text(
                                text = pet!!.petName,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        } else {
                            Text("No se encontró la mascota", color = MaterialTheme.colorScheme.error)
                        }

                        // Datos del usuario y la solicitud
                        Text(
                            text = "Datos del adoptante",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Nombre: ${request!!.userName} ${request!!.userSurname}")
                        Text("Edad: ${request!!.userAge}")
                        Text("Correo: ${request!!.userEmail}")
                        Text("Teléfono: ${request!!.userPhone}")

                        Text(
                            text = "Motivo de adopción",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(request!!.reason)

                        Text(
                            text = "Datos del entorno",
                            style = MaterialTheme.typography.titleMedium
                        )
                        // Ejemplo: ¿Actualmente tienes otros animales?
                        Text("¿Actualmente tienes otros animales? " +
                                if (request!!.currentlyHasPets) "Sí" else "No")
                        Text("¿Anteriormente has tenido animales? " +
                                if (request!!.previouslyHadPets) "Sí" else "No")
                        Text("¿Cuántas personas viven en tu casa?: ${request!!.peopleInHouse}")
                        Text("¿Todos en tu hogar están de acuerdo en adoptar? " +
                                if (request!!.allAgree) "Sí" else "No")
                        Text("¿Hay niños en casa?: " + if (request!!.hasKids) "Sí" else "No")
                        Text("¿Vives en casa propia?: " + if (request!!.ownHouse) "Sí" else "No")
                        if (!request!!.ownHouse) {
                            Text("¿Tus arrendadores permiten mascotas?: " +
                                    if (request!!.landlordAllows) "Sí" else "No")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botones para aprobar o rechazar
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    // Llamamos a updateRequestStatus con "aprobada"
                                    AdoptionRequestsRepository.updateRequestStatus(request!!.requestId, "aprobada") { success, error ->
                                        if (success) {
                                            // Podrías mostrar un snackbar, o simplemente:
                                            navController.popBackStack()
                                        } else {
                                            errorMessage = error ?: "Error al aprobar la solicitud."
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Aceptar")
                            }
                            Button(
                                onClick = {
                                    // Llamamos a updateRequestStatus con "rechazada"
                                    AdoptionRequestsRepository.updateRequestStatus(request!!.requestId, "rechazada") { success, error ->
                                        if (success) {
                                            navController.popBackStack()
                                        } else {
                                            errorMessage = error ?: "Error al rechazar la solicitud."
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                            ) {
                                Text("Rechazar")
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Función auxiliar para cargar la mascota dado su petId.
 */
private fun loadPet(
    petId: String,
    onResult: (PetData?, String?) -> Unit
) {
    if (petId.isBlank()) {
        onResult(null, "petId vacío.")
        return
    }
    val db = FirebaseFirestore.getInstance()
    db.collection("mascotas")
        .document(petId)
        .get()
        .addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val petData = snapshot.toObject(PetData::class.java)?.copy(id = snapshot.id)
                onResult(petData, null)
            } else {
                onResult(null, "No se encontró la mascota con ID $petId.")
            }
        }
        .addOnFailureListener { e ->
            onResult(null, e.localizedMessage)
        }
}
