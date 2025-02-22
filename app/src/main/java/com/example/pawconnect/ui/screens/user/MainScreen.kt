package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.Button
import com.example.pawconnect.Screen

@Composable
fun MainScreen(navController: NavController ) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    )
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Botón que utiliza navController para navegar a la pantalla de Login
            Button(onClick = { navController.navigate(Screen.Login.route) }) {
                Text(text = "Ir al Login", fontSize = 24.sp)
            }
        }
    }
}
