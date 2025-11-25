package com.example.proyectovideojuegos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.auth.VideojuegosView
import com.example.proyectovideojuegos.views.loginScreen
import com.example.proyectovideojuegos.views.registerScreen
import com.example.proyectovideojuegos.views.videojuegosFormEditarScreen
import com.example.proyectovideojuegos.views.videojuegosFormScreen
import videojuegosListScreen


@Composable //loginScreen es el ViewModel
fun AppNavigation(modifier: Modifier = Modifier,loginScreen: LoginScreen, videojuegosView: VideojuegosView) {
    val navController = rememberNavController()
    val startDestination = "login"

    NavHost(navController, startDestination = startDestination) {
        composable("login") {
            loginScreen(modifier,navController,loginScreen)
        }
        composable("register") {
            registerScreen(modifier,navController,loginScreen)
        }
        composable("videojuegosList") {
            videojuegosListScreen(modifier,navController,loginScreen,videojuegosView)
        }
        composable("videojuegosForm") {
            videojuegosFormScreen(modifier,navController,loginScreen,videojuegosView)
        }
        composable("videojuegosFormEditar/{videojuegoId}") {backStackEntry ->

            val videojuegoId = backStackEntry.arguments?.getString("videojuegoId")?.toIntOrNull() ?: -1
            videojuegosFormEditarScreen(modifier,navController,loginScreen,videojuegosView,videojuegoId)
        }
    }
}