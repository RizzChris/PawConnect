package com.example.pawconnect.ui.screens.shelter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.ShelterBottomNavBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("Cargando...") }
    var email by remember { mutableStateOf("Cargando...") }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Obtener usuario autenticado
    val user = auth.currentUser
    val userId = user?.uid ?: ""

    // Cargar datos desde Firestore
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("name") ?: "Nombre no disponible"
                        email = document.getString("email") ?: "Correo no disponible"
                    } else {
                        errorMessage = "No se encontró información"
                    }
                    isLoading = false
                }
                .addOnFailureListener { exception ->
                    errorMessage = exception.localizedMessage ?: "Error al obtener datos"
                    isLoading = false
                }
        } else {
            errorMessage = "Usuario no autenticado"
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            ShelterBottomNavBar(
                onHuellasClick = { navController.navigate(Screen.ShelterPets.route) },
                onHomeClick = { navController.navigate(Screen.ShelterHome.route) },
                onPerfilClick = { navController.navigate(Screen.ShelterProfile.route) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Logo y lema
            Text("PAWCONNECT", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Uniendo corazones, cambiando vidas", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))

            // Card con información del perfil
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen de perfil (Placeholder con fondo redondo)
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👤", fontSize = 40.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Mostrar datos cargados o indicador de carga
                    when {
                        isLoading -> CircularProgressIndicator()
                        errorMessage.isNotEmpty() -> Text(errorMessage, color = Color.Red)
                        else -> {
                            Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(text = email, fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Separador
            Divider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Botones de opciones
            ProfileButton("Revision de Pets") { navController.navigate(Screen.ShelterRevisionPetsScreen.route) }
            Spacer(modifier = Modifier.height(8.dp))
            ProfileButton("Guía de adopción") { navController.navigate(Screen.GuiaDeAdopcion.route) }
            Spacer(modifier = Modifier.height(8.dp))

            // Botón de cierre de sesión con icono
            Button(
                onClick = {
                    auth.signOut()
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.9f))
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja el contenido hacia arriba para dar espacio a la navbar
        }
    }
}

// Composable reutilizable para los botones de perfil
@Composable
fun ProfileButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text, fontSize = 16.sp)
    }
}
