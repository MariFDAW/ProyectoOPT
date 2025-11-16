package com.example.proyectovideojuegos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.navigation.AppNavigation
import com.example.proyectovideojuegos.ui.theme.ProyectoVideojuegosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val loginScreen : LoginScreen by viewModels()
        setContent {
            ProyectoVideojuegosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding -> AppNavigation(modifier = Modifier.padding(innerPadding),loginScreen = loginScreen)
                }
            }
        }
    }
}