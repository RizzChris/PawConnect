package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.UserBottomNavBar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    // Obtén el usuario actual de Firebase Auth
    val currentUser = FirebaseAuth.getInstance().currentUser
    // Si el displayName no está establecido, usa un valor por defecto
    val userName = currentUser?.displayName ?: "Usuario Ejemplo"
    // Obtén el email o usa un valor por defecto
    val userEmail = currentUser?.email ?: "usuario@email.com"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Perfil",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
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
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                // Imagen de usuario
                Image(
                    painter = painterResource(id = R.drawable.icon_user),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Mostrar el nombre y correo del usuario obtenidos desde Firebase
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                // Opcional: Si se muestra el valor por defecto, invita a actualizar el nombre
                if (userName == "Usuario Ejemplo") {
                    Text(
                        text = "Actualiza tu nombre para personalizar tu perfil",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Text(
                    text = userEmail,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Botones de opciones
                ProfileButton(text = "Editar perfil") { navController.navigate(Screen.EditProfileScreen.route)}
                ProfileButton(text = "Notificaciones") { /* Acción de notificaciones */ }
                ProfileButton(text = "Mis favoritos") { navController.navigate(Screen.Favorite.route) }
                ProfileButton(text = "Guía de adopción") { navController.navigate(Screen.GuiaDeAdopcion.route) }
                ProfileButton(text = "Cerrar sesión") { navController.navigate(Screen.Login.route) }
            }
        }
    }
}

@Composable
fun ProfileButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(text, fontSize = 16.sp)
    }
}

