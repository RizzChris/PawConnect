package com.example.pawconnect.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawconnect.R
import com.example.pawconnect.Screen
import com.example.pawconnect.repository.PetData
import com.example.pawconnect.repository.fetchPetsBySpecies
import com.example.pawconnect.ui.screens.components.FiltrosContent
import com.example.pawconnect.ui.screens.components.PetCard
import com.example.pawconnect.ui.screens.components.UserBottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDogsScreen(navController: NavController) {
    var petsList by remember { mutableStateOf<List<PetData>>(emptyList()) }
    var filteredPets by remember { mutableStateOf<List<PetData>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var showFiltros by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fetchPetsBySpecies("perro") { success, pets, error ->
            if (success) {
                petsList = pets ?: emptyList()
                filteredPets = petsList
            } else {
                errorMessage = error ?: "Error desconocido"
            }
            loading = false
        }
    }

    if (showFiltros) {
        ModalBottomSheet(
            onDismissRequest = { showFiltros = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            FiltrosContent(
                onFiltrosAplicados = { tamaño, genero ->
                    filteredPets = petsList.filter { pet ->
                        (tamaño.isEmpty() || pet.petSize == tamaño) &&
                                (genero.isEmpty() || pet.petSex == genero)
                    }
                    showFiltros = false
                }
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Perfil",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { showFiltros = true }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.filtro),
                                contentDescription = "Logo filtro",
                                modifier = Modifier.size(300.dp)
                            )
                        }
                    }
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
                        items(filteredPets) { pet ->
                            PetCard(
                                pet = pet,
                                onClickInfo = {
                                    navController.navigate(
                                        Screen.PetDetails.route.replace("{petId}", pet.id)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

