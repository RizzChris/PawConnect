package com.example.emptyactivity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emptyactivity.ui.screens.shelter.UserHomeScreen
import com.example.emptyactivity.ui.screens.shelter.ShelterHomeScreen
import com.example.emptyactivity.ui.screens.SignUpScreen
import com.example.emptyactivity.ui.screens.SuccessScreen
import com.example.emptyactivity.ui.screens.user.MainScreen
import com.example.emptyactivity.ui.screens.user.PetsScreen
import com.example.emptyactivity.ui.screens.user.ProfileScreen


// Definir las rutas de la app en una sola sealed class
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Success : Screen("success")
    object Home : Screen("home")
    object ShelterHome : Screen("shelter_home")
    object Main : Screen("main")
    object Pets : Screen("pets")
    object Profile : Screen("profile")
    object UserHome : Screen("user_home")
}

// Función que maneja la navegación
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { com.example.emptyactivity.ui.screens.LoginScreen(navController) }
        composable(Screen.Register.route) { SignUpScreen(navController) }
        composable(Screen.Success.route) { SuccessScreen(navController) }
        composable(Screen.Home.route) { UserHomeScreen(navController) }
        composable(Screen.ShelterHome.route) { ShelterHomeScreen(navController) }
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Pets.route) { PetsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.UserHome.route) { UserHomeScreen(navController) }

    }
}


