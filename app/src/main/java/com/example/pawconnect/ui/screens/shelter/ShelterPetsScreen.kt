package com.example.pawconnect.ui.screens.shelter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.ShelterBottomNavBar

@Composable
fun ShelterPetsScreen(navController: NavController) {
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
            // Columna que contendrá el logo, los botones y el cuadro gris
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 16.dp, end = 16.dp), // 15 dp desde arriba
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Logo PawConnect, más grande y clickeable
                Image(
                    painter = painterResource(id = R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                    contentDescription = "Logo PawConnect",
                    modifier = Modifier
                        .fillMaxWidth()         // Ocupa todo el ancho disponible
                        .height(100.dp)         // Aumenta la altura para hacerlo más grande
                        .clickable {
                            // Navegar a ShelterHome
                            navController.navigate(Screen.ShelterHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Fila de botones ("perros", "Gatos", "registrar")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre botones
                ) {
                    // Botón "perros"
                    Button(
                        onClick = { navController.navigate(Screen.ShelterDogsScreen.route) },
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
                        onClick = { navController.navigate(Screen.ShelterCatsScreen.route) },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("Gatos", fontSize = 16.sp)
                    }

                    // Botón "registrar"
                    Button(
                        onClick = { navController.navigate(Screen.ShelterRegistrationPets.route) },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D80))
                    ) {
                        Text("registrar", fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cuadro gris grande hasta el fondo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)                   // Ocupa todo el espacio vertical restante
                        .background(Color.Gray, shape = RoundedCornerShape(16.dp))
                )
            }
        }
    }
}