package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.ShelterBottomNavBar


@Composable
fun FormularioAdoptame(navController: NavController) {

    var petName by remember { mutableStateOf("") }
    var petSpecies by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petSize by remember { mutableStateOf("") }
    var petWeight by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petSex by remember { mutableStateOf("") }
    var petPhoto by remember { mutableStateOf("") }

    // Historia de la mascota
    var petHistory by remember { mutableStateOf("") }

    // Datos del entorno
    var isSterilized by remember { mutableStateOf(false) }
    var hasVaccines by remember { mutableStateOf(false) }
    var personality by remember { mutableStateOf("") }
    var medicalCondition by remember { mutableStateOf("") }
    var getAlongOtherAnimals by remember { mutableStateOf("No estoy segur@") }
    var getAlongKids by remember { mutableStateOf("No estoy segur@") }

    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            ShelterBottomNavBar(
                onHuellasClick = { navController.navigate(Screen.ShelterPets.route) },
                onHomeClick = { navController.navigate(Screen.ShelterHome.route) },
                onPerfilClick = { navController.navigate(Screen.ShelterProfile.route) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Logo PawConnect, clickeable
                Image(
                    painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                    contentDescription = "Logo PawConnect",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            navController.navigate(Screen.ShelterHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Cuadro gris que contendrá el formulario con scroll
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color(0xFFD7DCE2), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Sección: Datos de la mascota
                        Text("Datos de la mascota", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(
                            value = petName,
                            onValueChange = { input ->
                                // Validar que solo contenga letras y espacios
                                if (input.all { it.isLetter() || it.isWhitespace() }) {
                                    petName = input
                                }
                            },
                            label = { Text("Nombre de la mascota") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = petSpecies,
                            onValueChange = { petSpecies = it },
                            label = { Text("Especie (Perro o gato)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = petBreed,
                            onValueChange = { input ->
                                // Validar que solo contenga letras y espacios
                                if (input.all { it.isLetter() || it.isWhitespace() }) {
                                    petBreed = input
                                }
                            },
                            label = { Text("Raza") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        // Tamaño y Peso en la misma fila
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedTextField(
                                value = petSize,
                                onValueChange = { input ->
                                    // Validar que el tamaño sea "chico", "mediano" o "grande"
                                    val lower = input.lowercase()
                                    if (lower == "chico" || lower == "mediano" || lower == "grande") {
                                        petSize = lower
                                    }
                                },
                                label = { Text("Tamaño (chico, mediano, grande)") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = petWeight,
                                onValueChange = { input ->
                                    // Validar que sea un número (decimal)
                                    if (input.toDoubleOrNull() != null) {
                                        petWeight = input
                                    }
                                },
                                label = { Text("Peso") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        OutlinedTextField(
                            value = petAge,
                            onValueChange = { input ->
                                // Validar que sea un número entero
                                if (input.toIntOrNull() != null) {
                                    petAge = input
                                }
                            },
                            label = { Text("Edad aproximada") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        // Dropdown para Sexo: Se espera "macho" o "hembra"
                        OutlinedTextField(
                            value = petSex,
                            onValueChange = { input ->
                                val lower = input.lowercase()
                                if (lower == "macho" || lower == "hembra") {
                                    petSex = lower
                                }
                            },
                            label = { Text("Sexo (macho o hembra)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        // Fotografía de la mascota: Se espera una URL
                        OutlinedTextField(
                            value = petPhoto,
                            onValueChange = { petPhoto = it },
                            label = { Text("Fotografía de la mascota (URL)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        // Sección: Historia de la mascota
                        Text("Historia de la mascota", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Escribe la historia de la mascota en el recuadro.\n" +
                                    "Una vez que termines, pulsa Siguiente para continuar.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        OutlinedTextField(
                            value = petHistory,
                            onValueChange = { petHistory = it },
                            label = { Text("Escribe aquí...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                        // Sección: Datos del entorno
                        Text("Datos del entorno", style = MaterialTheme.typography.titleMedium)
                        // ¿Está esterilizado/a?
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("¿Está esterilizado/a? ")
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSterilized,
                                    onClick = { isSterilized = true }
                                )
                                Text("Sí")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !isSterilized,
                                    onClick = { isSterilized = false }
                                )
                                Text("No")
                            }
                        }
                        // ¿Tiene vacunas al día?
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("¿Tiene vacunas al día? ")
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = hasVaccines,
                                    onClick = { hasVaccines = true }
                                )
                                Text("Sí")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !hasVaccines,
                                    onClick = { hasVaccines = false }
                                )
                                Text("No")
                            }
                        }
                        OutlinedTextField(
                            value = personality,
                            onValueChange = { personality = it },
                            label = { Text("¿Cómo es su personalidad? Descríbelo aquí") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = medicalCondition,
                            onValueChange = { medicalCondition = it },
                            label = { Text("¿Tiene alguna condición médica o necesidades especiales?") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("¿Se lleva bien con otros animales?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf("Sí", "No", "No estoy segur@").forEach { option ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = (getAlongOtherAnimals == option),
                                        onClick = { getAlongOtherAnimals = option }
                                    )
                                    Text(option)
                                }
                            }
                        }
                        Text("¿Se lleva bien con niños?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf("Sí", "No", "No estoy segur@").forEach { option ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = (getAlongKids == option),
                                        onClick = { getAlongKids = option }
                                    )
                                    Text(option)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "¡Ha llegado al final de la encuesta!\n\n" +
                                    "En caso de tener algún error en alguna respuesta, " +
                                    "puede regresar a ver lo respondido y corregir en caso de algo incorrecto.\n\n" +
                                    "Si todo está correcto, pulse el botón de Finalizar, " +
                                    "siendo consciente y aceptando que toda la información proporcionada es verificable y verídica.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    // Lógica para "Regresar"
                                    // Por ejemplo: navController.popBackStack()
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                            ) {
                                Text("Regresar", fontSize = 16.sp)
                            }
                            Button(
                                onClick = {
                                    // Validar campos antes de llamar a registerPet
                                    when {
                                        petName.isBlank() || !petName.all { it.isLetter() || it.isWhitespace() } ->
                                            errorMessage = "El nombre debe contener solo letras y espacios."
                                        petSpecies.isBlank() ||
                                                (!petSpecies.equals("perro", ignoreCase = true) && !petSpecies.equals("gato", ignoreCase = true)) ->
                                            errorMessage = "La especie debe ser 'perro' o 'gato'."
                                        petBreed.isBlank() || !petBreed.all { it.isLetter() || it.isWhitespace() } ->
                                            errorMessage = "La raza debe contener solo letras y espacios."
                                        petSize.isBlank() || !petSize.all { it.isLetter() || it.isWhitespace() } ->
                                            //(petSize.lowercase() != "chico" && petSize.lowercase() != "mediano" && petSize.lowercase() != "grande") ->
                                            errorMessage = "El tamaño debe ser 'chico', 'mediano' o 'grande'."
                                        petWeight.isBlank() || petWeight.toDoubleOrNull() == null ->
                                            errorMessage = "El peso debe ser un número."
                                        petAge.isBlank() || petAge.toIntOrNull() == null ->
                                            errorMessage = "La edad debe ser un número entero."
                                        petSex.isBlank() || !petSex.all { it.isLetter() || it.isWhitespace() } ->
                                            //(petSex.lowercase() != "macho" && petSex.lowercase() != "hembra") ->
                                            errorMessage = "El sexo debe ser 'macho' o 'hembra'."
                                        petPhoto.isBlank() ->
                                            errorMessage = "Debe proporcionar una URL para la foto."
                                        petHistory.isBlank() ->
                                            errorMessage = "La historia no puede estar vacía."
                                        personality.isBlank() ->
                                            errorMessage = "La personalidad no puede estar vacía."
                                        else -> {
                                            errorMessage = ""
                                            // Llamar a la función registerPet del repository
                                            com.example.pawconnect.repository.registerPet(
                                                petName = petName,
                                                petSpecies = petSpecies,
                                                petBreed = petBreed,
                                                petSize = petSize,
                                                petWeight = petWeight,
                                                petAge = petAge,
                                                petSex = petSex,
                                                petPhoto = petPhoto,
                                                petHistory = petHistory,
                                                isSterilized = isSterilized,
                                                hasVaccines = hasVaccines,
                                                personality = personality,
                                                medicalCondition = medicalCondition,
                                                getAlongOtherAnimals = getAlongOtherAnimals,
                                                getAlongKids = getAlongKids
                                            ) { success, error ->
                                                if (success) {
                                                    // navega a una pantalla de éxito
                                                    navController.navigate(Screen.ShelterSuccess.route)
                                                } else {
                                                    errorMessage = error ?: "Error al registrar la mascota"
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                            ) {
                                Text("Enviar formulario", fontSize = 16.sp)
                            }
                        }
                        if (errorMessage.isNotEmpty()) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioAdoptamePreview() {
    val navController = rememberNavController()
    FormularioAdoptame(navController = navController)
}

