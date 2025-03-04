package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.pawconnect.repository.AdoptionRequest
import com.example.pawconnect.repository.AdoptionRequestsRepository
import com.example.pawconnect.ui.screens.components.UserBottomNavBar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioAdoptame(navController: NavController, petId: String) {

    // Obtenemos el usuario actual de FirebaseAuth
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Datos personales
    var userName by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var userSurname by remember { mutableStateOf("") }
    var userAge by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf(currentUser?.email ?: "") }
    var userPhone by remember { mutableStateOf(currentUser?.phoneNumber ?: "") }

    // Motivo de adopción
    var reason by remember { mutableStateOf("") }

    // Datos del entorno
    var currentlyHasPets by remember { mutableStateOf(false) }    // ¿Actualmente tienes otros animales?
    var previouslyHadPets by remember { mutableStateOf(false) }   // ¿Anteriormente has tenido animales?
    var peopleInHouse by remember { mutableStateOf("") }          // ¿Cuántas personas viven en tu casa?
    var allAgree by remember { mutableStateOf(false) }            // ¿Todos en tu hogar están de acuerdo en adoptar?
    var hasKids by remember { mutableStateOf(false) }             // ¿Hay niños en casa?
    var ownHouse by remember { mutableStateOf(false) }            // ¿Vives en casa propia?
    var landlordAllows by remember { mutableStateOf(false) }      // ¿Tus arrendadores permiten mascotas?

    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            UserBottomNavBar(
                onHuellasClick = { navController.navigate(Screen.Pets.route) },
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onPerfilClick = { navController.navigate(Screen.Profile.route) }
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo PawConnect
                Image(
                    painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                    contentDescription = "Logo PawConnect",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Cuadro gris con scroll
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
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // -- Sección: Datos personales --
                        Text("Datos personales", style = MaterialTheme.typography.titleMedium)

                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = userSurname,
                            onValueChange = { userSurname = it },
                            label = { Text("Apellidos") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = userAge,
                            onValueChange = { input ->
                                if (input.toIntOrNull() != null || input.isEmpty()) {
                                    userAge = input
                                }
                            },
                            label = { Text("Edad") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = userEmail,
                            onValueChange = { userEmail = it },
                            label = { Text("Correo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = userPhone,
                            onValueChange = { userPhone = it },
                            label = { Text("Teléfono") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // -- Sección: Motivo de adopción --
                        Text("¿Por qué deseas adoptar?", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(
                            value = reason,
                            onValueChange = { reason = it },
                            label = { Text("Escribe el motivo aquí...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        )

                        // -- Sección: Datos del entorno --
                        Text("Datos del entorno", style = MaterialTheme.typography.titleMedium)

                        // 1) ¿Actualmente tienes otros animales?
                        Text("¿Actualmente tienes otros animales?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = currentlyHasPets,
                                    onClick = { currentlyHasPets = true }
                                )
                                Text("Sí")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !currentlyHasPets,
                                    onClick = { currentlyHasPets = false }
                                )
                                Text("No")
                            }
                        }

                        // 2) ¿Anteriormente has tenido animales?
                        Text("¿Anteriormente has tenido animales?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = previouslyHadPets,
                                    onClick = { previouslyHadPets = true }
                                )
                                Text("Sí")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !previouslyHadPets,
                                    onClick = { previouslyHadPets = false }
                                )
                                Text("No")
                            }
                        }

                        // 3) ¿Cuántas personas viven en tu casa?
                        OutlinedTextField(
                            value = peopleInHouse,
                            onValueChange = { input ->
                                if (input.toIntOrNull() != null || input.isEmpty()) {
                                    peopleInHouse = input
                                }
                            },
                            label = { Text("¿Cuántas personas viven en tu casa?") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // 4) ¿Las personas que viven en tu hogar están de acuerdo en adoptar?
                        Text("¿Las personas en tu hogar están de acuerdo en adoptar?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = allAgree,
                                    onClick = { allAgree = true }
                                )
                                Text("Sí")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !allAgree,
                                    onClick = { allAgree = false }
                                )
                                Text("No")
                            }
                        }

                        // 5) ¿Hay niños en casa?
                        Text("¿Hay niños en casa?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = hasKids,
                                    onClick = { hasKids = true }
                                )
                                Text("Sí")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !hasKids,
                                    onClick = { hasKids = false }
                                )
                                Text("No")
                            }
                        }

                        // 6) ¿Vives en casa propia?
                        Text("¿Vives en casa propia?")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = ownHouse,
                                    onClick = { ownHouse = true }
                                )
                                Text("Sí")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !ownHouse,
                                    onClick = { ownHouse = false }
                                )
                                Text("No")
                            }
                        }

                        // 7) ¿Tus arrendadores permiten mascotas? (solo si ownHouse = false)
                        if (!ownHouse) {
                            Text("¿Tus arrendadores permiten mascotas?")
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = landlordAllows,
                                        onClick = { landlordAllows = true }
                                    )
                                    Text("Sí")
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = !landlordAllows,
                                        onClick = { landlordAllows = false }
                                    )
                                    Text("No")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        // Texto final
                        Text(
                            text = "¡Ha llegado al final de la encuesta!\n\n" +
                                    "En caso de tener algún error en alguna respuesta, " +
                                    "puede regresar a ver lo respondido y corregirlo.\n\n" +
                                    "Si todo está correcto, pulse el botón de Finalizar, " +
                                    "siendo consciente y aceptando que toda la información proporcionada es verificable y verídica.",
                            style = MaterialTheme.typography.bodySmall
                        )

                        // Botones
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    // Lógica para "Regresar"
                                    navController.popBackStack()
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                            ) {
                                Text("Regresar", fontSize = 16.sp)
                            }
                            Button(
                                onClick = {
                                    // Validar campos
                                    when {
                                        userName.isBlank() -> {
                                            errorMessage = "Por favor, ingresa tu nombre."
                                        }
                                        userSurname.isBlank() -> {
                                            errorMessage = "Por favor, ingresa tus apellidos."
                                        }
                                        userAge.isBlank() -> {
                                            errorMessage = "Por favor, ingresa tu edad."
                                        }
                                        userEmail.isBlank() -> {
                                            errorMessage = "Por favor, ingresa tu correo."
                                        }
                                        userPhone.isBlank() -> {
                                            errorMessage = "Por favor, ingresa tu teléfono."
                                        }
                                        reason.isBlank() -> {
                                            errorMessage = "Por favor, indica por qué deseas adoptar."
                                        }
                                        else -> {
                                            // Si todo está bien, creamos la solicitud y la guardamos en Firestore
                                            errorMessage = ""

                                            val newRequest = AdoptionRequest(
                                                userName = userName,
                                                userSurname = userSurname,
                                                userAge = userAge,
                                                userEmail = userEmail,
                                                userPhone = userPhone,
                                                reason = reason,
                                                currentlyHasPets = currentlyHasPets,
                                                previouslyHadPets = previouslyHadPets,
                                                peopleInHouse = peopleInHouse,
                                                allAgree = allAgree,
                                                hasKids = hasKids,
                                                ownHouse = ownHouse,
                                                landlordAllows = landlordAllows,
                                                petId = petId
                                            )

                                            AdoptionRequestsRepository.addAdoptionRequest(newRequest) { success, error ->
                                                if (success) {
                                                    navController.navigate(Screen.Success.route)
                                                } else {
                                                    errorMessage = error ?: "Error al guardar la solicitud"
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
                                Text("Finalizar", fontSize = 16.sp)
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




