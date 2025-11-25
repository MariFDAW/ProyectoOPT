import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.AuthState
import com.example.proyectovideojuegos.auth.LoginScreen

@Composable
fun videojuegosListScreen(
    modifier: Modifier, navController: NavController, loginScreen: LoginScreen,
    rolUsuario: String
){
    val authState = loginScreen.authState.observeAsState() // Esta linea es placeholder

    val rolUsuario = loginScreen.rolUsuario.observeAsState()

    //Mostrar lista de videojuegos con foreach
    if (rolUsuario.value == "admin") {
        // Mostrar botones editar+eliminar
        Column(
            modifier = modifier.fillMaxSize().background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate("videojuegosForm")
            },
                enabled = authState.value != AuthState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Añadir",
                )
            }
        }
    }



}