package com.example.pawconnect.ui.screens.user

import android.webkit.URLUtil.isValidUrl
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.repository.PetData
import com.example.pawconnect.repository.fetchAllPets
import com.example.pawconnect.ui.screens.components.ShelterBottomNavBar
import android.util.Patterns





@Composable
fun PetsScreen(navController: NavController) {
    // Estados para la lista de mascotas y la carga
    var petsList by remember { mutableStateOf<List<PetData>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    // Al iniciar la pantalla, se cargan las mascotas desde Firestore
    LaunchedEffect(Unit) {
        fetchAllPets { success, pets, error ->
            if (success) {
                petsList = pets
            } else {
                errorMessage = error ?: "Error desconocido"
            }
            loading = false
        }
    }

    // Estructura principal con Scaffold y la barra inferior
    Scaffold(
        bottomBar = {
            // Barra de navegación inferior (NavBar)
            ShelterBottomNavBar(
                onHuellasClick = { navController.navigate(Screen.ShelterPets.route) },
                onHomeClick = { navController.navigate(Screen.ShelterHome.route) },
                onPerfilClick = { navController.navigate(Screen.ShelterProfile.route) }
            )
        }
    ) { innerPadding ->
        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Columna con el logo, la fila de botones y el cuadro gris donde se muestran las mascotas
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Logo PawConnect, clickeable
                Image(
                    painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                    contentDescription = "Logo PawConnect",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            navController.navigate(Screen.ShelterHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Fila de botones ("perros", "gatos", "registrar")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre botones
                ) {
                    // Botón "perros"
                    Button(
                        onClick = { /* Acción para filtrar perros */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("perros", fontSize = 16.sp)
                    }

                    // Botón "Gatos"
                    Button(
                        onClick = { /* Acción para filtrar gatos */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("Gatos", fontSize = 16.sp)
                    }

                    // Botón "registrar" (o "Filtros", según tu preferencia)
                    Button(
                        onClick = { /* Acción para registrar o filtrar */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("Filtros", fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cuadro gris donde se mostrarán las mascotas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray)
                        .padding(16.dp)
                ) {
                    // Aquí mostramos el estado de carga, error o la lista de mascotas
                    when {
                        loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        errorMessage.isNotEmpty() -> {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        else -> {
                            // Mostrar la lista de mascotas en un LazyColumn
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(petsList) { pet ->
                                    PetCard(pet = pet, onClickInfo = {
                                        // Acción al hacer clic en "Ver información"
                                        // navController.navigate(Screen.PetDetails.route) etc.
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
fun isValidUrl(url: String): Boolean {
    return Patterns.WEB_URL.matcher(url).matches()
}
// Modelo de tarjeta para mostrar cada mascota
@Composable
fun PetCard(pet: PetData, onClickInfo: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7DCE2))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val imageModel = if (isValidUrl(pet.petPhoto)) pet.petPhoto else null
            // Imagen de la mascota (si usas Coil, ajusta la import)
            val painter = rememberAsyncImagePainter(
                model = imageModel,
                fallback = painterResource(R.drawable.my_placeholder),
                error = painterResource(R.drawable.my_placeholder)
            )
            Image(
                painter = painter,
                contentDescription = pet.petName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre de la mascota
            Text(text = pet.petName, style = MaterialTheme.typography.titleMedium)

            // Ejemplo: sexo y edad en una fila
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                if (pet.petSex.isNotBlank()) {
                    Chip(text = pet.petSex.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    })
                }
                if (pet.petAge.isNotBlank()) {
                    Chip(text = "${pet.petAge} años")
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Botón "Ver información"
            Button(onClick = onClickInfo) {
                Text("Ver información")
            }
        }
    }
}

// Un pequeño composable para mostrar el "Chip" (etiqueta redondeada)
@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFFFD966),
        modifier = Modifier
            .height(28.dp)
            .wrapContentWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
