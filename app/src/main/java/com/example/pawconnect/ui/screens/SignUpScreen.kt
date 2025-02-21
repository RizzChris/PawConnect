package com.example.pawconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun SignUpScreen(navController: NavController) {
    // Estados para cada campo
    var tipoCuenta by remember { mutableStateOf("Selecciona un tipo de cuenta") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
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
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(48.dp) // Tamaño del botón
                .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                .background(Color(0xFF4A5D80)) // Color de fondo
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, // Icono de flecha
                contentDescription = "Regresar",
                tint = Color.White // Color del icono
            )
        }
        // Columna principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo PawConnect
            Image(
                painter = painterResource(id = R.drawable.logo_pawconnect),
                contentDescription = "Logo PawConnect",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta blanca para el formulario
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
                    // Dropdown para Tipo de cuenta
                    TipoCuentaDropdown(
                        tipoCuentaSeleccionado = tipoCuenta,
                        onTipoCuentaChange = { tipoCuenta = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Campo de nombre (solo letras y espacios)
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nuevoValor ->
                            if (nuevoValor.all { it.isLetter() || it.isWhitespace() }) {
                                nombre = nuevoValor
                            }
                        },
                        label = { Text("Nombre") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Campo de apellido (solo letras y espacios)
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { nuevoValor ->
                            if (nuevoValor.all { it.isLetter() || it.isWhitespace() }) {
                                apellido = nuevoValor
                            }
                        },
                        label = { Text("Apellido") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Apellido") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Campo de correo electrónico (validación mínima de caracteres)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { nuevoValor ->
                            // Permitimos letras, dígitos, @, ., _, y -
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

                    // Campo de teléfono (solo dígitos)
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { nuevoValor ->
                            if (nuevoValor.all { it.isDigit() }) {
                                telefono = nuevoValor
                            }
                        },
                        label = { Text("Teléfono") },
                        leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "Teléfono") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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

                    // Botón "Crear mi cuenta"
                    Button(
                        onClick = {
                            errorMessage = ""
                            when {
                                tipoCuenta.isBlank() || nombre.isBlank() || apellido.isBlank()
                                        || email.isBlank() || telefono.isBlank() || password.isBlank() ->
                                    errorMessage = "Por favor, completa todos los campos."
                                else -> {
                                    // Aquí podrías llamar a tu repositorio o ViewModel para registrar al usuario
                                    navController.navigate(Screen.Success.route)

                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("Crear mi cuenta", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mensaje de error
                    if (errorMessage.isNotEmpty()) {
                        Text(errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

/**
 * Dropdown simple para seleccionar el tipo de cuenta (por ejemplo, "Usuario" o "Refugio").
 */
@Composable
fun TipoCuentaDropdown(
    tipoCuentaSeleccionado: String,
    onTipoCuentaChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = tipoCuentaSeleccionado,
            onValueChange = {},
            label = { Text("Tipo de cuenta") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Desplegar menú")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Usuario", "Refugio").forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onTipoCuentaChange(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}

