package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pawconnect.Screen
import com.example.pawconnect.ui.screens.components.UserBottomNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



@Composable
fun UserHomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            // Barra de navegación inferior (NavBar)
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
                .background(Color.White)
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
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryButton(
                    iconRes = painterResource(id = com.example.pawconnect.R.drawable.icon_dog),
                    label = "Perros",
                    onClick = {  navController.navigate(Screen.UserDogs.route) }
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
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = iconRes,
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                tint = Color.Black
            )
        }
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}


