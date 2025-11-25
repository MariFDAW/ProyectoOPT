import android.widget.Button
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectovideojuegos.auth.AuthState
import com.example.proyectovideojuegos.auth.LoginScreen
import com.example.proyectovideojuegos.auth.VideojuegosView
import com.example.proyectovideojuegos.models.Videojuegos

@Composable
fun videojuegosListScreen(
    modifier: Modifier, navController: NavController, loginScreen: LoginScreen,videojuegosView: VideojuegosView = viewModel()) {

    val rolUsuario by loginScreen.rolUsuario.observeAsState("usuario")

    val videojuegos by videojuegosView.videojuegos.observeAsState(emptyList())
    val loading by videojuegosView.loading.observeAsState(false)
    val error by videojuegosView.error.observeAsState()

    //Mostrar lista de videojuegos con foreach
    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cargando lista de videojuegos...",
                    color = Color.LightGray
                )
            }
        }
    } else if (videojuegos.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = "No hay videojuegos por el momento...",
                    color = Color.LightGray
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(videojuegos) { videojuego ->
                VideojuegoCard(
                    videojuego = videojuego
                )
            }
        }

    }
}

@Composable
fun VideojuegoCard(videojuego: Videojuegos) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Título y ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = videojuego.nombre,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Estado y Calificación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Estado: " + videojuego.completado,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${videojuego.calificacion}/5",
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Añadido: ${videojuego.fecha}",
            )
            // Reseña
            if (videojuego.resenia.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reseña:",
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = videojuego.resenia,
                )
            }
        }
    }
}
