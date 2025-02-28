package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pawconnect.R
import com.example.pawconnect.repository.PetData
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(navController: NavController, petId: String) {
    var pet by remember { mutableStateOf<PetData?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(petId) {
        FirebaseFirestore.getInstance()
            .collection("mascotas")
            .document(petId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    pet = document.toObject(PetData::class.java)?.copy(id = petId)
                } else {
                    errorMessage = "Mascota no encontrada"
                }
                loading = false
            }
            .addOnFailureListener { e ->
                errorMessage = e.localizedMessage ?: "Error desconocido"
                loading = false
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = pet?.petName ?: "Detalle de la Mascota",
                        color = MaterialTheme.colorScheme.onPrimary)
                        },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_huella),
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Agregar a favoritos */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_favorite),
                            contentDescription = "Favorito",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                loading -> CircularProgressIndicator()
                errorMessage.isNotEmpty() -> {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                pet != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Imagen de la mascota
                        val painter = rememberAsyncImagePainter(
                            model = pet!!.petPhoto,
                            fallback = painterResource(R.drawable.my_placeholder),
                            error = painterResource(R.drawable.my_placeholder)
                        )
                        Image(
                            painter = painter,
                            contentDescription = pet!!.petName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Chip(text = pet!!.petColor, iconRes = R.drawable.icon_color)
                            Chip(text = pet!!.petBreed, iconRes = R.drawable.icon_dog)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Chip(text = "${pet!!.petAge} años", iconRes = R.drawable.icon_age)
                            Chip(text = pet!!.petSex, iconRes = R.drawable.icon_gender)
                            Chip(text = "${pet!!.petWeight} kg", iconRes = R.drawable.icon_weight)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Chip(text = pet!!.petSize, iconRes = R.drawable.icon_size)
                            Chip(text = "Sí", iconRes = R.drawable.icon_vaccine)
                            Chip(text = "Sí", iconRes = R.drawable.icon_sterilized)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Historia:",
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = pet!!.petHistory,
                                    fontSize = 14.sp,
                                    color = Color.DarkGray,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { /* Acción para adoptar */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Adóptame", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Chip(text: String, iconRes: Int) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFFFD966), // Amarillo
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = text, fontSize = 14.sp)
        }
    }
}