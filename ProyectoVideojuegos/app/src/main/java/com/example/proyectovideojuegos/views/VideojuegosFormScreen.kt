import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
fun videojuegosFormScreen(modifier: Modifier, navController: NavController, loginScreen: LoginScreen, rolUsuario: String) {
    val rolUsuario = loginScreen.rolUsuario.observeAsState()

    if (rolUsuario.value == "admin") { //Comprobar rol
        // Mostrar formulario de creación/edición con los campos necesarios
        //Boton de guardar
    } else {
        navController.navigate("videojuegosList")
        Text("No tienes permisos para acceder a esta sección")
    }
}