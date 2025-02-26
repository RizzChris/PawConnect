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
import com.example.pawconnect.ui.screens.shelter.PlantillaRefugio
import com.example.pawconnect.ui.screens.user.PlantillaUsers


// Definir las rutas de la app en una sola sealed class
sealed class Screen(val route: String) {
    //autenticación
    object Login : Screen("login")
    object Register : Screen("register")
    object Success : Screen("success")

    //usuario
    data object Home : Screen("home")
    data object Main : Screen("main")
    data object Pets : Screen("pets")
    data object Profile : Screen("profile")
    data object UserHome : Screen("user_home")

    //refugio
    data object ShelterHome : Screen("shelter_home")
    data object ShelterProfile : Screen("shelter_profile")
    data object ShelterRegistrationPets : Screen("shelter_registration_pets")
    data object ShelterPets : Screen("shelter_pets")
    data object ShelterFormularioPets : Screen("shelter_formulario_pets")
    data object ShelterPruebas : Screen("shelter_pruebas")
    data object ShelterSuccess : Screen("shelter_success")




    data object PlantillaRefugio : Screen("plantilla_refugio")
    data object PlantillaUsers : Screen("plantilla_users")

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
        composable(Screen.PlantillaRefugio.route) { PlantillaRefugio(navController) }
        composable(Screen.PlantillaUsers.route) { PlantillaUsers(navController) }


    }
}


