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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.AuthState
import com.example.proyectovideojuegos.auth.LoginScreen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

@Composable
fun videojuegosListScreen(modifier: Modifier, navController: NavController, loginScreen: LoginScreen){

    val rolUsuario = loginScreen.rolUsuario.observeAsState()

    //Mostrar lista de videojuegos con foreach
    if (rolUsuario.value == "admin") {
        // Mostrar botones editar+eliminar
    }



}