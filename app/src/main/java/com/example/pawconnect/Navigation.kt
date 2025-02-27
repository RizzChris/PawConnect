package com.example.pawconnect

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pawconnect.ui.screens.LoginScreen
import com.example.pawconnect.ui.screens.SignUpScreen
import com.example.pawconnect.ui.screens.SuccessScreen
import com.example.pawconnect.ui.screens.shelter.ShelterHomeScreen
import com.example.pawconnect.ui.screens.shelter.ShelterProfileScreen
import com.example.pawconnect.ui.screens.shelter.ShelterRegistrationPetsScreen
import com.example.pawconnect.ui.screens.shelter.ShelterPetsScreen
import com.example.pawconnect.ui.screens.shelter.ShelterFormularioPetsScreen
import com.example.pawconnect.ui.screens.shelter.ShelterSuccess
import com.example.pawconnect.ui.screens.shelter.ShelterPruebas
import com.example.pawconnect.ui.screens.shelter.PlantillaRefugio
import com.example.pawconnect.ui.screens.user.PetsScreen
import com.example.pawconnect.ui.screens.user.ProfileScreen
import com.example.pawconnect.ui.screens.user.UserHomeScreen
import com.example.pawconnect.ui.screens.user.UserDogsScreen
import com.example.pawconnect.ui.screens.user.UserCatsScreen
import com.example.pawconnect.ui.screens.user.PetDetailScreen
import com.example.pawconnect.ui.screens.user.PlantillaUsers

sealed class Screen(val route: String) {
    // Autenticación
    object Login : Screen("login")
    object Register : Screen("register")
    object Success : Screen("success")

    // Usuario
    object Home : Screen("home")
    object Pets : Screen("pets")
    object Profile : Screen("profile")
    object UserHome : Screen("user_home")
    object UserDogs : Screen("user_dogs")
    object UserCats : Screen("user_cats")
    object UserRegistrationPets : Screen("user_registration_pets")
    object PetDetails : Screen("pet_details/{petId}")

    // Refugio
    object ShelterHome : Screen("shelter_home")
    object ShelterProfile : Screen("shelter_profile")
    object ShelterRegistrationPets : Screen("shelter_registration_pets")
    object ShelterPets : Screen("shelter_pets")
    object ShelterFormularioPets : Screen("shelter_formulario_pets")
    object ShelterSuccess : Screen("shelter_success")
    object ShelterPruebas : Screen("shelter_pruebas")

    // Otros
    object PlantillaRefugio : Screen("plantilla_refugio")
    object PlantillaUsers : Screen("plantilla_users")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { SignUpScreen(navController) }
        composable(Screen.Success.route) { SuccessScreen(navController) }

        // Usuario
        composable(Screen.Home.route) { UserHomeScreen(navController) }
        composable(Screen.Pets.route) { PetsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.UserHome.route) { UserHomeScreen(navController) }
        composable(Screen.UserDogs.route) { UserDogsScreen(navController) }
        composable(Screen.UserCats.route) { UserCatsScreen(navController) }
        composable(Screen.UserRegistrationPets.route) { ShelterFormularioPetsScreen(navController) }
        composable(
            route = Screen.PetDetails.route,
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            PetDetailScreen(navController, petId)
        }

        // Refugio
        composable(Screen.ShelterHome.route) { ShelterHomeScreen(navController) }
        composable(Screen.ShelterProfile.route) { ShelterProfileScreen(navController) }
        composable(Screen.ShelterRegistrationPets.route) { ShelterRegistrationPetsScreen(navController) }
        composable(Screen.ShelterPets.route) { ShelterPetsScreen(navController) }
        composable(Screen.ShelterFormularioPets.route) { ShelterFormularioPetsScreen(navController) }
        composable(Screen.ShelterSuccess.route) { ShelterSuccess(navController) }
        composable(Screen.ShelterPruebas.route) { ShelterPruebas(navController) }
        composable(Screen.PlantillaRefugio.route) { PlantillaRefugio(navController) }
        composable(Screen.PlantillaUsers.route) { PlantillaUsers(navController) }
    }
}




