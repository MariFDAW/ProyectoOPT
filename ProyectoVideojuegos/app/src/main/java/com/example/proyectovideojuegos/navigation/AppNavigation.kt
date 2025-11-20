package com.example.proyectovideojuegos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.views.homeScreen
import com.example.proyectovideojuegos.views.loginScreen
import com.example.proyectovideojuegos.views.registerScreen
import videojuegosFormScreen
import videojuegosListScreen


@Composable //loginScreen es el ViewModel
fun AppNavigation(modifier: Modifier = Modifier,loginScreen: LoginScreen) {
    val navController = rememberNavController()
    val startDestination = "login"

    NavHost(navController, startDestination = startDestination) {
        composable("login") {
            loginScreen(modifier,navController,loginScreen)
        }
        composable("register") {
            registerScreen(modifier,navController,loginScreen)
        }
        composable("home/{email}") {
            backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            homeScreen(modifier,navController,loginScreen,email)
        }
        composable("videojuegosList") {
            videojuegosListScreen(modifier,navController,loginScreen)
        }
        composable("videojuegosForm/{rolUsuario}") {
                backStackEntry ->
            val rolUsuario = backStackEntry.arguments?.getString("rolUsuario") ?: "usuario"
            videojuegosFormScreen(modifier,navController,loginScreen,rolUsuario)
        }
    }
}