package com.example.proyectovideojuegos.auth

import androidx.lifecycle.ViewModel
import com.example.proyectovideojuegos.models.Videojuegos
import com.google.firebase.database.FirebaseDatabase

class VideojuegosView : ViewModel() {
    private val database = FirebaseDatabase.getInstance(
        "https://proyectovideojuegos-fbd46-default-rtdb.europe-west1.firebasedatabase.app"
    ).reference.child("videojuegos")

    fun insertarVideojuego(nombre: String, completado: String, calificacion: Int, resenia: String) {
        database.orderByChild("videojuegoId").limitToLast(1).get()
            .addOnSuccessListener { resultado ->
                //Cojo el id más grande osea el último
                val ultimoId = resultado.children.firstOrNull() //Obtiene el primer hijo
                    ?.child("videojuegoId")?.getValue(Int::class.java) ?: 0
                val nuevoUltimoId = ultimoId + 1 //Incremento en 1

                val videojuego = Videojuegos(
                    videojuegoId = nuevoUltimoId,
                    nombre = nombre,
                    completado = completado,
                    calificacion = calificacion,
                    resenia = resenia
                )

                database.child(nuevoUltimoId.toString()).setValue(videojuego)
            }
    }
}