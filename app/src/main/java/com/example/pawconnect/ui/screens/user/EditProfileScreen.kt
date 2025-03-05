package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.UserBottomNavBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavHostController) {
    // Obtén el usuario actual de Firebase Auth
    val currentUser = FirebaseAuth.getInstance().currentUser
    var name by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                    text = "Editar Perfil",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_huella),
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    currentUser?.let { user ->
                        // Actualiza el nombre
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user.updateProfile(profileUpdates).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Nombre actualizado correctamente
                                message = "Nombre actualizado correctamente"
                            } else {
                                message = "Error al actualizar el nombre"
                            }
                        }
                        // Actualiza el email
                        user.updateEmail(email).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                message += "\nCorreo actualizado correctamente"
                            } else {
                                message += "\nError al actualizar el correo"
                            }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Guardar cambios")
            }
            if (message.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = message, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
