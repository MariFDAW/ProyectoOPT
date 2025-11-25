package com.example.proyectovideojuegos.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.auth.VideojuegosView

@Composable
fun videojuegosFormScreen(
    modifier: Modifier, navController: NavController, loginScreen: LoginScreen, videojuegosView: VideojuegosView){

    val rolUsuario by loginScreen.rolUsuario.observeAsState("usuario")
    val transaccionCompletada by videojuegosView.transaccionCompletada.observeAsState()

    var nombre by remember {
        mutableStateOf("")
    }
    var completado by remember {
        mutableStateOf("")
    }
    var calificacion by remember {
        mutableStateOf("")
    }
    var resenia by remember {
        mutableStateOf("")
    }

    var clickado by remember { mutableStateOf(false) }
    val opcionesEstado = listOf("Completado", "En curso", "Pendiente")

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        videojuegosView.limpiarTransaccion()
    }

    DisposableEffect(Unit) {
        onDispose {
            videojuegosView.limpiarTransaccion()
        }
    }

    if (rolUsuario == "admin") {

        LaunchedEffect(transaccionCompletada) {
            if (transaccionCompletada == true) {
                navController.navigate("videojuegosList") {
                    popUpTo("videojuegosForm") { inclusive = true }
                }
                videojuegosView.limpiarTransaccion()
            }
        }

    } else {
        LaunchedEffect(Unit) {
            navController.navigate("videojuegosList") {
                popUpTo("videojuegosForm") { inclusive = true }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Añadir",
            fontSize = 42.sp,
            color = Color.White
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        OutlinedTextField(
            value = nombre ,
            onValueChange = {
                nombre = it
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
                    text = "Nombre",
                    color = Color.White
                )
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )
        OutlinedTextField(
            value = calificacion ,
            onValueChange = {
                calificacion = it
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
                    text = "Calificación",
                    color = Color.White
                )
            }
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Text(
            text = "Estado",
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp)
        ){
            Button(
                onClick = { clickado = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DB954),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = completado,
                    color = Color.Black
                )
            }

            DropdownMenu(
                expanded = clickado,
                onDismissRequest = { clickado = false }
            ) {
                opcionesEstado.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            completado = opcion
                            videojuegosView.completado.value = opcion
                            clickado = false
                        }
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        OutlinedTextField(
            value = resenia ,
            onValueChange = {
                resenia = it
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
                    text = "Reseña",
                    color = Color.White
                )
            }
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = {
                if (nombre.isBlank() || completado.isBlank() || calificacion.isBlank()) {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    videojuegosView.insertarVideojuego(
                        nombre,
                        completado,
                        calificacion.toIntOrNull() ?: 0,
                        resenia
                    )
                }

            },colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1DB954),
                contentColor = Color.Black
            )) {
                Text(
                    text = "Añadir"
                )
            }
            Button(onClick = {
                navController.navigate("videojuegosList")
            },colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1DB954),
                contentColor = Color.Black
            )) {
                Text(
                    text = "Cancelar"
                )
            }
        }
    }
}