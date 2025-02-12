package com.example.emptyactivity.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emptyactivity.Screen
import com.example.emptyactivity.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_dogs), // Asegúrate de agregar la imagen a res/drawable
            contentDescription = "Fondo de perros",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenedor centrado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_pawconnect),
                contentDescription = "Logo PawConnect",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta blanca
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Campo de correo electrónico
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Campo de contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mantener sesión iniciada
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = true, onCheckedChange = {})
                        Text("Mantener la sesión iniciada")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Botón de login
                    Button(
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                errorMessage = "Por favor ingresa todos los campos"
                            } else {
                                navController.navigate(Screen.Home.route)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("Iniciar sesión", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mensaje de error
                    if (errorMessage.isNotEmpty()) {
                        Text(errorMessage, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Botones de redes sociales
                    Text("Registrarse con", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_facebook),
                            contentDescription = "Facebook",
                            modifier = Modifier
                                .size(90.dp)
                                .clickable { /* Acción de Facebook */ }
                        )

                        Spacer(modifier = Modifier.width(20.dp)) // Aumenta el espacio entre iconos

                        Image(
                            painter = painterResource(id = R.drawable.icon_google),
                            contentDescription = "Google",
                            modifier = Modifier
                                .size(90.dp)
                                .clickable { /* Acción de Google */ }
                        )

                        Spacer(modifier = Modifier.width(20.dp)) // Espacio entre iconos

                        Image(
                            painter = painterResource(id = R.drawable.icon_apple),
                            contentDescription = "Apple",
                            modifier = Modifier
                                .size(90.dp)
                                .clickable { /* Acción de Apple */ }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Link de registro
                    Text(
                        text = "¿No tienes una cuenta? Regístrate aquí",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable { navController.navigate(Screen.Register.route) } // ← Aquí navega a RegisterScreen
                            .padding(8.dp)
                    )

                }
            }
        }
    }
}

