package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.UserBottomNavBar

@Composable
fun UserHomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            UserBottomNavBar(
                onHuellasClick = { navController.navigate(Screen.Pets.route) },
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onPerfilClick = { navController.navigate(Screen.Profile.route) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = com.example.pawconnect.R.drawable.logo_pawconnectuniendocorazonescambiandovidas),
                contentDescription = "Logo PawConnect",
                modifier = Modifier.height(50.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = com.example.pawconnect.R.drawable.refugio_dogs_2),
                contentDescription = "Imagen Destacada",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryButton(
                    iconRes = painterResource(id = com.example.pawconnect.R.drawable.icon_dog),
                    label = "Perros",
                    onClick = { navController.navigate(Screen.UserDogs.route) }
                )
                CategoryButton(
                    iconRes = painterResource(id = com.example.pawconnect.R.drawable.icon_cat),
                    label = "Gatos",
                    onClick = { navController.navigate(Screen.UserCats.route) }
                )
            }
        }
    }
}

@Composable
fun CategoryButton(iconRes: Painter, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer), // Adaptado a modo claro/oscuro
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = iconRes,
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer // Adaptado a modo claro/oscuro
            )
        }
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground // Adaptado a modo oscuro
        )
    }
}