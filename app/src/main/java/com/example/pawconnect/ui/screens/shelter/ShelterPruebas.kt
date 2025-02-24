package com.example.pawconnect.ui.screens.shelter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pawconnect.Screen

@Composable
fun ShelterPruebas(navController: NavController) {
    Column(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a tu Perfil")
        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Login.route) }) {
            Text("Cerrar Sesión")
        }
    }
}



