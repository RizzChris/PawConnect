package com.example.emptyactivity.ui.screens.shelter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emptyactivity.R
import com.example.emptyactivity.Screen
import kotlinx.coroutines.delay

@Composable
fun ShelterHomeScreen(navController: NavController) {
    // Lista de imágenes para el carrusel
    val imageList = listOf(
        R.drawable.refugio_dogs_1,
        R.drawable.refugio_dogs_2,
        R.drawable.refugio_dogs_3
    )
    // Índice actual de la imagen mostrada
    var currentImageIndex by remember { mutableStateOf(0) }

    // Efecto para cambiar la imagen cada 5 segundos
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000) // Espera 5 segundos
            currentImageIndex = (currentImageIndex + 1) % imageList.size
        }
    }

    Scaffold(
        bottomBar = {
            // Barra de navegación inferior
            BottomNavBar(
                onHuellasClick = { /* Navegar o manejar acción de huellas */ },
                onHomeClick = { /* Navegar o manejar acción de home */ },
                onPerfilClick = { /* Navegar o manejar acción de perfil */ }
            )
        }
    ) { innerPadding ->
        // Contenido principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Logo PawConnect (arriba de todo)
                Image(
                    painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                    contentDescription = "Logo PawConnect",
                    modifier = Modifier
                        .size(300.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))

                // Carrusel de imágenes
                Image(
                    painter = painterResource(id = imageList[currentImageIndex]),
                    contentDescription = "Carrusel Refugio",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje de bienvenida
                Text(
                    text = "Bienvenid@: Patitas de amor",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Logo Patitas de amor
                Image(
                    painter = painterResource(id = R.drawable.logopatitasdeamor),
                    contentDescription = "Logo Patitas de amor",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                // Espacio para empujar la barra inferior hacia abajo
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * Barra de navegación inferior con 3 íconos:
 * - Huellas
 * - Home
 * - Perfil
 */

@Composable
fun BottomNavBar(
    onHuellasClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    NavigationBar {
        // Ícono huellas
        NavigationBarItem(
            selected = false,
            onClick = { onHuellasClick() },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_huella),
                    contentDescription = "Huellas"
                )
            },
            label = { Text("Huellas", fontSize = 10.sp) }
        )

        // Ícono home
        NavigationBarItem(
            selected = true, // Si esta pantalla es el "Home", márcalo como seleccionado
            onClick = { onHomeClick() },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_home),
                    contentDescription = "Home"
                )
            },
            label = { Text("Home", fontSize = 10.sp) }
        )

        // Ícono perfil
        NavigationBarItem(
            selected = false,
            onClick = { onPerfilClick() },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_user),
                    contentDescription = "Perfil"
                )
            },
            label = { Text("Perfil", fontSize = 10.sp) }
        )
    }
}
