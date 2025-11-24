import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.LoginScreen

@Composable
fun videojuegosListScreen(
    modifier: Modifier, navController: NavController, loginScreen: LoginScreen,
    rolUsuario: String
){

    val rolUsuario = loginScreen.rolUsuario.observeAsState()

    //Mostrar lista de videojuegos con foreach
    if (rolUsuario.value == "admin") {
        // Mostrar botones editar+eliminar
    }



}