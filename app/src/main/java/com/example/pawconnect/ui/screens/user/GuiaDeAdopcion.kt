package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.UserBottomNavBar
import kotlinx.coroutines.delay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuiaDeAdopcion(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                        contentDescription = "Logo PawConnect",
                        modifier = Modifier.size(250.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_huella),
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Permite desplazamiento
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {

                Text(
                    text = "GUÍA DE ADOPCIÓN",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    //modifier = Modifier.padding(start = 12.dp) // Espaciado entre la flecha y el texto
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descubre cómo puedes cambiar una vida y encontrar un amigo para siempre",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección "¿Por qué adoptar?"
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD4C3FC)) // Color lila
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_qm),
                        contentDescription = "Question Mark",
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "¿Por qué adoptar?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Al adoptar no solo cambias la vida de un animal, sino que también contribuyes a reducir el problema de mascotas sin hogar en Nuevo León.",
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección "Proceso de Adopción"
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF2B6DE)) // Color rosa
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Proceso de Adopción",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Paso 1 - Prepararte para adoptar
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_uc),
                            contentDescription = "User Check",
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "Prepárate para adoptar",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Asegúrate de tener el tiempo, espacio y recursos necesarios para cuidar a una mascota.",
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Paso 2 - Encuentra tu compañero ideal
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "Heart",
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "Encuentra tu compañero ideal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Explora los perfiles de mascotas y elige una que se ajuste a tu estilo de vida.",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            val imageList = listOf(
                R.drawable.refugio_dogs_1,
                R.drawable.refugio_dogs_2,
                R.drawable.refugio_dogs_3
            )
            var currentImageIndex by remember { mutableStateOf(0) }

            LaunchedEffect(key1 = currentImageIndex) {
                delay(5000) // Espera 5 segundos
                currentImageIndex = (currentImageIndex + 1) % imageList.size
            }

            Image(
                painter = painterResource(id = imageList[currentImageIndex]),
                contentDescription = "Imagen del proceso de adopción",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

