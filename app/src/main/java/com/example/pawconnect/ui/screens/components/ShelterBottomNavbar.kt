package com.example.pawconnect.ui.screens.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.pawconnect.R

@Composable
fun ShelterBottomNavBar(
    onHuellasClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { onHuellasClick() },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_huella), contentDescription = "Huellas") },
            label = { Text("Mascotas", fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = false, // Marca como seleccionado si se está en MainScreen
            onClick = { onHomeClick() },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_home), contentDescription = "Home") },
            label = { Text("Inicio", fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onPerfilClick() },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = "Perfil") },
            label = { Text("Perfil", fontSize = 10.sp) }
        )
    }
}
