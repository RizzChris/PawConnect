package com.example.pawconnect.ui.screens.user

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.UserBottomNavBar
import androidx.compose.ui.platform.LocalContext
import com.example.pawconnect.repository.NotificationPreferences


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPreferences(navController: NavController) {
    // Cargar configuración previamente guardada
    val context = LocalContext.current
    var seguimientoSolicitudes by remember { mutableStateOf(false) }
    var avisoNuevasMascotas by remember { mutableStateOf(false) }
    var infoMascotas by remember { mutableStateOf(false) }
    var envioEncuesta by remember { mutableStateOf(false) }
    var actualizacionesDisponibles by remember { mutableStateOf(false) }

    // Cargar las configuraciones al iniciar
    LaunchedEffect(Unit) {
        val settings = NotificationPreferences.loadNotificationSettings(context)
        seguimientoSolicitudes = settings.seguimientoSolicitudes
        avisoNuevasMascotas = settings.avisoNuevasMascotas
        infoMascotas = settings.infoMascotas
        envioEncuesta = settings.envioEncuesta
        actualizacionesDisponibles = settings.actualizacionesDisponibles
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Notificaciones",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Interruptores para las opciones de notificaciones
                Text(text = "Seguimiento de solicitudes")
                Switch(
                    checked = seguimientoSolicitudes,
                    onCheckedChange = { seguimientoSolicitudes = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Aviso de nuevas mascotas")
                Switch(
                    checked = avisoNuevasMascotas,
                    onCheckedChange = { avisoNuevasMascotas = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Información sobre mascotas")
                Switch(
                    checked = infoMascotas,
                    onCheckedChange = { infoMascotas = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Envío de encuesta")
                Switch(
                    checked = envioEncuesta,
                    onCheckedChange = { envioEncuesta = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Actualizaciones disponibles")
                Switch(
                    checked = actualizacionesDisponibles,
                    onCheckedChange = { actualizacionesDisponibles = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar la configuración
                Button(
                    onClick = {
                        // Guardar configuración
                        NotificationPreferences.saveNotificationSettings(
                            context,
                            seguimientoSolicitudes,
                            avisoNuevasMascotas,
                            infoMascotas,
                            envioEncuesta,
                            actualizacionesDisponibles
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Guardar configuración", fontSize = 16.sp)
                }
            }
        }
    }
}
