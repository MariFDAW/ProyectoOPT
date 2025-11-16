package com.example.proyectovideojuegos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.AuthState
import com.example.proyectovideojuegos.auth.LoginScreen

@Composable
fun homeScreen(modifier: Modifier, navController: NavController, loginScreen: LoginScreen, email: String){
    val authState = loginScreen.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Column (
        modifier = modifier.fillMaxSize().background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(
            text="Home" ,
            fontSize = 42.sp,
            color = Color.White

        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Text(
            text= email,
            color = Color.LightGray
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Button(onClick = {
            loginScreen.cerrarSesion()
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Cerrar Sesión"
            )
        }
    }

}