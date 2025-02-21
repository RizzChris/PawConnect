package com.example.pawconnect.ui.screens

import androidx.compose.foundation.Image
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
import com.example.pawconnect.Screen
import com.example.pawconnect.R


@Composable
fun LoginScreen(navController: NavController) {
    // Estados para cada campo
    var tipoCuenta by remember { mutableStateOf("Selecciona tipo de cuenta") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_dogs),
            contentDescription = "Fondo de perros",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna principal
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
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TipoCuentaDropdown(
                        tipoCuentaSeleccionado = tipoCuenta,
                        onTipoCuentaChange = { tipoCuenta = it }
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Campo de correo electrónico
                    OutlinedTextField(
                        value = email,
                        onValueChange = { nuevoValor ->
                            // Permitimos letras, dígitos, @, ., _ y -
                            if (nuevoValor.matches(Regex("^[A-Za-z0-9@._-]*$"))) {
                                email = nuevoValor
                            }
                        },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Correo") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Campo de contraseña (oculta caracteres)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón "Iniciar Sesión"
                    Button(
                        onClick = {
                            errorMessage = ""
                            when {
                                tipoCuenta == "Selecciona tipo de cuenta" ||
                                        email.isBlank() || password.isBlank() ->
                                    errorMessage = "Por favor, completa todos los campos."
                                !isValidEmail(email) ->
                                    errorMessage = "El correo electrónico no es válido."
                                else -> {
                                    if (email == "refugio@gmail.com" && password == "123") {
                                        navController.navigate(Screen.ShelterHome.route)
                                    } else {
                                        navController.navigate(Screen.UserHome.route)
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("Iniciar Sesión", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Link de registro
                    Text(
                        text = "¿No tienes una cuenta? -> Regístrate aquí",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable { navController.navigate(Screen.Register.route) }
                            .padding(8.dp)
                    )

                    // Mensaje de error
                    if (errorMessage.isNotEmpty()) {
                        Text(errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
