package com.example.emptyactivity.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emptyactivity.R

@Composable
fun SignUpScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
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
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = Color.White // Color del icono
            )
        }

        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { if (it.all { char -> char.isLetter() || char.isWhitespace() }) nombre = it },
                    label = { Text("Nombre") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = apellido,
                    onValueChange = { if (it.all { char -> char.isLetter() || char.isWhitespace() }) apellido = it },
                    label = { Text("Apellido") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { if (it.all { char -> char.isDigit() }) telefono = it },
                    label = { Text("Teléfono") },
                    leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DropdownMenuExample(
                        label = "Municipio",
                        options = listOf("CDMX", "Guadalajara", "Monterrey"),
                        onSelection = { municipio = it },
                        modifier = Modifier.weight(1f)
                    )
                    DropdownMenuExample(
                        label = "Género",
                        options = listOf("Masculino", "Femenino", "Otro"),
                        onSelection = { genero = it },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { println("Municipio seleccionado: $municipio, Género seleccionado: $genero") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                ) {
                    Text("Crear mi cuenta", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun DropdownMenuExample(
    label: String,
    options: List<String>,
    onSelection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Abrir menú")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedText = option
                        onSelection(option)
                        expanded = false
                    }
                )
            }
        }
    }
}



