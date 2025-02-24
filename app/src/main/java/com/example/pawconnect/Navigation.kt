package com.example.pawconnect

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawconnect.ui.screens.SuccessScreen
import com.example.pawconnect.ui.screens.user.MainScreen
import com.example.pawconnect.ui.screens.user.PetsScreen
import com.example.pawconnect.ui.screens.user.ProfileScreen
import com.example.pawconnect.ui.screens.SignUpScreen
import com.example.pawconnect.ui.screens.shelter.ShelterFormularioPetsScreen
import com.example.pawconnect.ui.screens.shelter.ShelterHomeScreen
import com.example.pawconnect.ui.screens.shelter.ShelterPetsScreen
import com.example.pawconnect.ui.screens.shelter.ShelterProfileScreen
import com.example.pawconnect.ui.screens.shelter.ShelterPruebas
import com.example.pawconnect.ui.screens.shelter.ShelterRegistrationPetsScreen
import com.example.pawconnect.ui.screens.user.UserHomeScreen
import com.example.pawconnect.ui.screens.shelter.ShelterSuccess


// Definir las rutas de la app en una sola sealed class
sealed class Screen(val route: String) {
    //autenticación
    object Login : Screen("login")
    object Register : Screen("register")
    object Success : Screen("success")

    //usuario
    object Home : Screen("home")
    object Main : Screen("main")
    object Pets : Screen("pets")
    object Profile : Screen("profile")
    object UserHome : Screen("user_home")
    //refugio
    object ShelterHome : Screen("shelter_home")
    object ShelterProfile : Screen("shelter_profile")
    object ShelterRegistrationPets : Screen("shelter_registration_pets")
    object ShelterPets : Screen("shelter_pets")
    object ShelterFormularioPets : Screen("shelter_formulario_pets")
    object ShelterPruebas : Screen("shelter_pruebas")
    object ShelterSuccess : Screen("shelter_success")
}

// Función que maneja la navegación
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        // Definir las rutas de la app
        composable(Screen.Login.route) { com.example.pawconnect.ui.screens.LoginScreen(navController) }
        composable(Screen.Register.route) { SignUpScreen(navController) }
        composable(Screen.Success.route) { SuccessScreen(navController) }

        //usuario
        composable(Screen.Home.route) { UserHomeScreen(navController) }
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Pets.route) { PetsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.UserHome.route) { UserHomeScreen(navController) }
        //refugio
        composable(Screen.ShelterHome.route) { ShelterHomeScreen(navController) }
        composable(Screen.ShelterProfile.route) { ShelterProfileScreen(navController) }
        composable(Screen.ShelterRegistrationPets.route) { ShelterRegistrationPetsScreen(navController) }
        composable(Screen.ShelterPets.route) { ShelterPetsScreen(navController) }
        composable(Screen.ShelterRegistrationPets.route) { ShelterFormularioPetsScreen(navController) }
        composable(Screen.ShelterSuccess.route) { ShelterSuccess(navController) }


        composable(Screen.ShelterPruebas.route) { ShelterPruebas(navController) }
    }
}


