package com.example.emptyactivity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emptyactivity.ui.screens.user.HomeScreen
import com.example.emptyactivity.ui.screens.SignUpScreen
import com.example.emptyactivity.ui.screens.SuccessScreen


// Definir las rutas de la app en una sola sealed class
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Success : Screen("success")
    object Home : Screen("home")
}

// Función que maneja la navegación
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            com.example.emptyactivity.ui.screens.LoginScreen(
                navController
            )
        }
        composable(Screen.Register.route) { SignUpScreen(navController) }
        composable(Screen.Success.route) { SuccessScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }

    }
}

