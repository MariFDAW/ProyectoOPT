import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.auth.VideojuegosView

@Composable
fun videojuegosFormScreen(
    modifier: Modifier, navController: NavController, loginScreen: VideojuegosView){

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
/*
    val authState = loginScreen.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("")
            is AuthState.Error -> Toast.makeText(context,(
                    authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }*/

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
        OutlinedTextField(
            value = completado ,
            onValueChange = {
                completado = it
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
                    text = "Estado",
                    color = Color.White
                )
            }
        )
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

        Button(onClick = {
            loginScreen.insertarVideojuego(
                nombre,
                completado,
                calificacion.toIntOrNull() ?: 0,
                resenia
            )
        },) {
            Text(
                text = "Añadir"
            )
        }
    }
}