package com.example.proyectovideojuegos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.auth.VideojuegosView

@Composable
fun videojuegosFormEditarScreen(
    modifier: Modifier, navController: NavController, loginScreen: LoginScreen, videojuegosView: VideojuegosView,videojuegoId:Int){

    val rolUsuario by loginScreen.rolUsuario.observeAsState("usuario")
    val transaccionCompletada by videojuegosView.transaccionCompletada.observeAsState()

    val nombreVideojuego by videojuegosView.nombre.observeAsState("")
    val completadoVideojuego by videojuegosView.completado.observeAsState("")
    val calificacionVideojuego by videojuegosView.calificacion.observeAsState(0)
    val reseniaVideojuego by videojuegosView.resenia.observeAsState("")

    var nombre by remember {
        mutableStateOf(videojuegosView.nombre.value)
    }
    var completado by remember {
        mutableStateOf(videojuegosView.completado.value)
    }
    var calificacion  by remember {
        mutableStateOf(videojuegosView.calificacion.value)
    }
    var resenia by remember {
        mutableStateOf(videojuegosView.resenia.value)
    }

    var clickado by remember {
        mutableStateOf(false)
    }
    val opcionesEstado = listOf("Completado", "En curso", "Pendiente")


    //Este metodo se ejecuta cuando se crea el componente
    LaunchedEffect(Unit) {
        videojuegosView.limpiarTransaccion()
    }

    //Este metodo se ejecuta cuando se desmonta el componente
    DisposableEffect(Unit) {
        onDispose {
            videojuegosView.limpiarTransaccion()
        }
    }

    LaunchedEffect(videojuegoId) {
        videojuegosView.cargarVideojuegoAModificar(videojuegoId)
    }

    LaunchedEffect(nombreVideojuego, completadoVideojuego, calificacionVideojuego, reseniaVideojuego) {

        nombre = nombreVideojuego
        completado = completadoVideojuego
        calificacion = calificacionVideojuego
        resenia = reseniaVideojuego

    }

    if (rolUsuario == "admin") {

        LaunchedEffect(transaccionCompletada) {
            if (transaccionCompletada == true) {
                navController.navigate("videojuegosList") {
                    popUpTo("videojuegosForm") { inclusive = true }
                }
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
            text = "Actualizar",
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
            value = calificacion.toString() ,
            onValueChange = { calificacionIntroducida ->
                videojuegosView.calificacion.value = calificacionIntroducida.toIntOrNull() ?: 0
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
        Box {
            Button(
                onClick = { clickado = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DB954),
                    contentColor = Color.White
                )
            ) {
                Text(completado)
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
                videojuegosView.actualizarVideojuego(
                    videojuegoId,
                    nombre,
                    completado,
                    calificacion,
                    resenia
                )

            },colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )) {
                Text(
                    text = "Actualizar Datos"
                )
            }
            Button(onClick = {
                navController.navigate("videojuegosList")
            },colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )) {
                Text(
                    text = "Cancelar"
                )
            }
        }

    }
}