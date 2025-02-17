package com.example.emptyactivity.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emptyactivity.R
import com.example.emptyactivity.Screen



@Composable
fun SuccessScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo con blur
        Image(
            painter = painterResource(id = R.drawable.background_dogs),
            contentDescription = "Fondo de perros",
            modifier = Modifier
                .fillMaxSize()
                .blur(16.dp), // Ajusta el valor del blur a lo que necesites
            contentScale = ContentScale.Crop
        )

        // Card sin blur, centrada en la pantalla
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Por ejemplo, un ícono de éxito:
                Icon(
                    painter = painterResource(id = R.drawable.logo_ic_check), // Tu recurso de check
                    contentDescription = "Check de éxito",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "¡Su cuenta ha sido creada con éxito!",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Su refugio ya está listo para darle hogar a las mascotas que tanto lo necesitan. Para continuar inicie sesión.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Iniciar sesión", fontSize = 16.sp)
                }
            }
        }
    }
}

