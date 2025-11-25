package com.example.proyectovideojuegos.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectovideojuegos.models.Videojuegos
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class VideojuegosView : ViewModel() {

    private val _videojuegos = MutableLiveData<List<Videojuegos>>()
    val videojuegos: LiveData<List<Videojuegos>> = _videojuegos
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val database = FirebaseDatabase.getInstance(
        "https://proyectovideojuegos-fbd46-default-rtdb.europe-west1.firebasedatabase.app"
    ).reference.child("videojuegos")


    init {
        cargarListaVideojuegos()
    }

    private fun cargarListaVideojuegos() {
        _loading.value = true

        database.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val listaVideojuegos = mutableListOf<Videojuegos>()

                    for (videojuegoSnapshot in snapshot.children) {
                        val videojuego = videojuegoSnapshot.getValue(Videojuegos::class.java)
                        videojuego?.let { listaVideojuegos.add(it) }
                    }

                    _videojuegos.value = listaVideojuegos
                    _loading.value = false
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = "Error al cargar videojuegos: ${error.message}"
                    _loading.value = false
                }

            })
    }

    fun insertarVideojuego(nombre: String, completado: String, calificacion: Int, resenia: String) {


        database.orderByChild("videojuegoId").limitToLast(1).get()
            .addOnSuccessListener { resultado ->
                //Cojo el id más grande osea el último
                val ultimoId = resultado.children.firstOrNull() //Obtiene el primer hijo
                    ?.child("videojuegoId")?.getValue(Int::class.java) ?: 0
                val nuevoUltimoId = ultimoId + 1 //Incremento en 1

                val fecha = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                val fechaFormateada= fecha.format(Date())

                val videojuegos = HashMap<String, Any>()
                videojuegos["videojuegosId"] = nuevoUltimoId
                videojuegos["nombre"] = nombre
                videojuegos["completado"] = completado
                videojuegos["calificacion"] = calificacion
                videojuegos["resenia"] = resenia
                videojuegos["fecha"] = fechaFormateada


                database.setValue(videojuegos)
                    .addOnSuccessListener {
                        _error.value = null
                    }
                    .addOnFailureListener { exception ->
                        _error.value = "Error al insertar en la bd"
                    }
            }.addOnFailureListener { exception ->
                _loading.value = false
                _error.value = "Error al obtener ID al insertar"
            }
    }
}