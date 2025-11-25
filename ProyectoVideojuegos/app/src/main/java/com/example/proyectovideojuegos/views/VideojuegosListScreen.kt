import android.widget.Button
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.AuthState
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.auth.VideojuegosView
import com.example.proyectovideojuegos.models.Videojuegos
import com.google.firebase.database.FirebaseDatabase

@Composable
fun videojuegosListScreen(
    modifier: Modifier, navController: NavController, loginScreen: LoginScreen,videojuegosView: VideojuegosView) {

    val rolUsuario by loginScreen.rolUsuario.observeAsState("usuario")
    val authState = loginScreen.authState.observeAsState()

    val videojuegos by videojuegosView.videojuegos.observeAsState(emptyList())

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Título
        item {
            Text(
                "Mis Videojuegos",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { loginScreen.cerrarSesion() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DB954),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Salir")
                }

                if (rolUsuario == "admin") {
                    Button(
                        onClick = { navController.navigate("videojuegosForm") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1DB954),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Añadir Videojuego")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }


     if (videojuegos.isEmpty()) {
        item {
            Text(
                text = "No hay videojuegos por el momento...",
                color = Color.LightGray
            )
        }
    } else {
        items(videojuegos) { videojuego ->
            VideojuegoTarjeta(videojuego, rolUsuario, navController)
        }
        }
    }

}

@Composable
fun VideojuegoTarjeta(
    videojuego: Videojuegos,
    rolUsuario: String,
    navController: NavController) {

    val context = LocalContext.current
    val videojuegosRef = FirebaseDatabase.getInstance().getReference("videojuegos")

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1DB954)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = videojuego.nombre,
                    color = Color.Black
                )
                if (rolUsuario == "admin") {
                    IconButton(onClick = {
                        navController.navigate("videojuegosFormEditar/${videojuego.videojuegoId}")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar videojuego",
                            tint = Color.Black
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Estado: " + videojuego.completado,
                        color = Color.Black
                    )
                    if (videojuego.resenia.isNotEmpty()) {
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                        Text(
                            text = "Reseña:",
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = videojuego.resenia,
                            color = Color.Black
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${videojuego.calificacion}/5",
                        color = Color.Black
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
            HorizontalDivider(
                color = Color.Black
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Añadido: ${videojuego.fecha}",
                    color = Color.Black
                )
                if (rolUsuario == "admin") {
                    IconButton(onClick = {
                        videojuegosRef.child(videojuego.videojuegoId.toString()).removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(context, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al eliminar: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar videojuego",
                            tint = Color.Red
                        )
                    }
                }
            }

        }
    }
}
