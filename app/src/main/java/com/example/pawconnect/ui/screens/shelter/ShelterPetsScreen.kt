package com.example.pawconnect.ui.screens.shelter


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawconnect.Screen
import com.example.pawconnect.R
import com.example.pawconnect.ui.screens.components.ShelterBottomNavBar


@Composable
fun ShelterPetsScreen(navController: NavController) {
    val errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // Barra personalizada (sin TopAppBar)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de regreso
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }

                // Título
                Text(
                    text = "Registrar",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        },
        bottomBar = {
            ShelterBottomNavBar(
                onHuellasClick = { navController.navigate(Screen.ShelterPets.route) },
                onHomeClick = { navController.navigate(Screen.ShelterHome.route) },
                onPerfilClick = { navController.navigate(Screen.ShelterProfile.route) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Ícono de la encuesta
                Icon(
                    painter = painterResource(id = R.drawable.icon_encuesta),
                    contentDescription = "Icono de la encuesta",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción de la encuesta
                Text(
                    text = "Para continuar con el proceso de registro, será necesario llenar una encuesta.\n\n" +
                            "La encuesta tiene como objetivo, recolectar información acerca de la mascota, así como algunos detalles.\n\n" +
                            "Cuando se encuentre listo, de clic en 'Comenzar encuesta'.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botón "Comenzar encuesta"
                Button(
                    onClick = {
                        navController.navigate(Screen.ShelterRegistrationPets.route)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                ) {
                    Text("Comenzar encuesta", fontSize = 16.sp)
                }

                // Mostrar mensaje de error
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}
