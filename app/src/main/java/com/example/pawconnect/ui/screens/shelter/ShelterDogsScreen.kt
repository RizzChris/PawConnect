package com.example.pawconnect.ui.screens.shelter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pawconnect.Screen
import com.example.pawconnect.repository.PetData
import com.example.pawconnect.repository.fetchPetsBySpecies
import com.example.pawconnect.ui.screens.components.PetCard
import com.example.pawconnect.ui.screens.components.UserBottomNavBar


@Composable
fun ShelterDogsScreen(navController: NavController) {
    var petsList by remember { mutableStateOf<List<PetData>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        fetchPetsBySpecies("perro") { success, pets, error ->
            if (success) {
                petsList = pets
            } else {
                errorMessage = error ?: "Error desconocido"
            }
            loading = false
        }
    }

    Scaffold(
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
                .padding(innerPadding)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage.isNotEmpty() -> {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(petsList) { pet ->
                            PetCard(pet = pet, onClickInfo = {
                                navController.navigate(
                                    Screen.PetDetails.route.replace("{petId}", pet.id)
                                )
                            })
                        }
                    }
                }
            }
        }
    }
}
