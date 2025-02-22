package com.example.pawconnect.ui.screens.shelter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.BottomNavBar
import kotlinx.coroutines.delay

@Composable
fun UserHomeScreen(navController: NavController) {
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

    // Scaffold con bottomBar
    Scaffold(
        bottomBar = {
            BottomNavBar(
                onHuellasClick = { navController.navigate(Screen.Pets.route) },
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onPerfilClick = { navController.navigate(Screen.Profile.route) }
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

                // Logo PawConnect
                Image(
                    painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                    contentDescription = "Logo PawConnect",
                    modifier = Modifier.size(300.dp)
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

                // Empuja la barra inferior al final
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

