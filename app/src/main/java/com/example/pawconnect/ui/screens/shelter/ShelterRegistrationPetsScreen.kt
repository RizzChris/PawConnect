package com.example.pawconnect.ui.screens.shelter

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.input.KeyboardType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember




@Composable
fun ShelterRegistrationPetsScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var tamaño by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var historia by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // Función para seleccionar imagen
    val context = LocalContext.current
    val onImagePicked: (Uri) -> Unit = { uri -> imageUri = uri }

    // Crea un formulario para ingresar los datos de la mascota
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre de la mascota") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = especie, onValueChange = { especie = it }, label = { Text("Especie (Perro o Gato)") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = raza, onValueChange = { raza = it }, label = { Text("Raza") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = tamaño, onValueChange = { tamaño = it }, label = { Text("Tamaño") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = peso, onValueChange = { peso = it }, label = { Text("Peso") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = sexo, onValueChange = { sexo = it }, label = { Text("Sexo") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = historia, onValueChange = { historia = it }, label = { Text("Historia de la mascota") })
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para seleccionar imagen
        Button(onClick = { /* Lógica para seleccionar imagen */ }) {
            Text("Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para registrar la mascota
        Button(
            onClick = {
                if (nombre.isNotEmpty() && especie.isNotEmpty() && raza.isNotEmpty() && imageUri != null) {
                    uploadImageToStorage(imageUri!!) { imageUrl ->
                        if (imageUrl != null) {
                            val petData = mapOf(
                                "nombre" to nombre,
                                "especie" to especie,
                                "raza" to raza,
                                "tamaño" to tamaño,
                                "peso" to peso,
                                "edad" to edad,
                                "sexo" to sexo,
                                "historia" to historia
                            )
                            savePetDataToFirestore(petData, imageUrl) { success ->
                                if (success) {
                                    navController.navigate("SuccessRoute")
                                } else {
                                    errorMessage = "Hubo un error al guardar los datos."
                                }
                            }
                        } else {
                            errorMessage = "Error al subir la imagen."
                        }
                    }
                } else {
                    errorMessage = "Por favor, completa todos los campos."
                }
            }
        ) {
            Text("Registrar mascota")
        }

        // Mostrar mensaje de error
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }
}

// Función para subir la imagen a Firebase Storage
fun uploadImageToStorage(imageUri: Uri, onComplete: (String?) -> Unit) {
    val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    val petImageRef = storageReference.child("pets_photos/${imageUri.lastPathSegment}")

    petImageRef.putFile(imageUri).addOnSuccessListener {
        petImageRef.downloadUrl.addOnSuccessListener { uri ->
            onComplete(uri.toString()) // Devuelve la URL de la imagen subida
        }
    }.addOnFailureListener {
        onComplete(null) // Si falla la subida
    }
}

// Función para guardar los datos de la mascota en Firestore
fun savePetDataToFirestore(petData: Map<String, Any>, imageUrl: String?, onComplete: (Boolean) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId != null && imageUrl != null) {
        val firestore = FirebaseFirestore.getInstance()

        // Crear un documento de mascota en la subcolección "pets"
        firestore.collection("refugios")
            .document(userId) // Asumiendo que el refugio está identificado por el UID del usuario
            .collection("pets")
            .add(petData + mapOf("fotografia" to imageUrl)) // Guarda los datos de la mascota
            .addOnSuccessListener {
                onComplete(true) // Mascota guardada exitosamente
            }
            .addOnFailureListener {
                onComplete(false) // Error al guardar la mascota
            }
    } else {
        onComplete(false) // Si el usuario no está autenticado o la imagen es nula
    }
}
