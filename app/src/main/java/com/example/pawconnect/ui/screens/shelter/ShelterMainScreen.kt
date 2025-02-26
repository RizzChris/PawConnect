package com.example.pawconnect.ui.screens.shelter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.pawconnect_logo), // Reemplaza con tu logo
            contentDescription = "PawConnect Logo",
            modifier = Modifier.height(50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.dog_high_five), // Reemplaza con tu imagen
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
                iconRes = R.drawable.ic_dog, // Reemplaza con ícono de perro
                label = "Perros",
                onClick = { navController.navigate("perros_menu") }
            )
            CategoryButton(
                iconRes = R.drawable.ic_cat, // Reemplaza con ícono de gato
                label = "Gatos",
                onClick = { navController.navigate("gatos_menu") }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavigationBar(navController)
    }
}

@Composable
fun CategoryButton(iconRes: Int, label: String, onClick: () -> Unit) {
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
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                tint = Color.Black
            )
        }
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}



