package com.example.proyectovideojuegos.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.AuthState
import com.example.proyectovideojuegos.auth.LoginScreen

@Composable
fun loginScreen(modifier: Modifier, navController: NavController,loginScreen: LoginScreen){

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authState = loginScreen.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated ->
                navController.navigate("videojuegosList")
            is AuthState.Error ->
                Toast.makeText(context,(authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 42.sp,
            color = Color.White
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        OutlinedTextField(
            value = email ,
            onValueChange = {
                email = it
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF1DB954),
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black
            ),
            label = {
               Text(
                   text = "Email",
                   color = Color.White
               )
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )
        OutlinedTextField(
            value = password ,
            onValueChange = {
                password = it
            },colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF1DB954),
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black
            ),
            visualTransformation = PasswordVisualTransformation(),
            label = {
                Text(
                    text = "Contraseña",
                    color = Color.White
                )
            }
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(onClick = {/*
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(context,"Ambos deben estar rellenos", Toast.LENGTH_SHORT).show()

            }*/
            loginScreen.login(email,password)
        },
            enabled = authState.value != AuthState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )
            ) {
            Text(
                text = "Iniciar Sesión",
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(onClick = {
            navController.navigate("register")
        }) {

            Text(
                text = "No tienes cuenta, Registrate",
                color = Color.LightGray
            )
        }
    }
}