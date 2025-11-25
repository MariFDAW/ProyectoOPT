package com.example.proyectovideojuegos.auth

import android.widget.Toast
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

    private val _transaccionCompletada = MutableLiveData<Boolean>()
    val transaccionCompletada: LiveData<Boolean> = _transaccionCompletada
    private val database = FirebaseDatabase.getInstance(
        "https://proyectovideojuegos-fbd46-default-rtdb.europe-west1.firebasedatabase.app"
    ).reference.child("videojuegos")

    val nombre = MutableLiveData("")
    val completado = MutableLiveData("")
    val calificacion = MutableLiveData(0)
    val resenia = MutableLiveData("")



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
                videojuegos["videojuegoId"] = nuevoUltimoId
                videojuegos["nombre"] = nombre
                videojuegos["completado"] = completado
                videojuegos["calificacion"] = calificacion
                videojuegos["resenia"] = resenia
                videojuegos["fecha"] = fechaFormateada


                database.child(nuevoUltimoId.toString()).setValue(videojuegos)
                    .addOnSuccessListener {
                        _error.value = null
                        _transaccionCompletada.value = true
                    }
                    .addOnFailureListener { exception ->
                        _error.value = "Error al insertar en la bd"
                        _transaccionCompletada.value = false
                    }
            }.addOnFailureListener { exception ->
                _loading.value = false
                _error.value = "Error al obtener ID al insertar"
            }
    }
    fun cargarVideojuegoAModificar(videojuegoId: Int) {

        if (videojuegoId == -1) return

        database.child(videojuegoId.toString()).get().addOnSuccessListener {
            result ->
            val videojuego = result.getValue(Videojuegos::class.java)
            nombre.value = videojuego?.nombre ?: ""
            completado.value = videojuego?.completado ?: ""
            calificacion.value = videojuego?.calificacion ?: 0
            resenia.value = videojuego?.resenia ?: ""
        }
    }
    fun actualizarVideojuego(videojuegoId: Int, nombre: String, completado: String, calificacion: Int, resenia: String) {
        val datosActualizados = mapOf(
            "nombre" to nombre,
            "completado" to completado,
            "calificacion" to calificacion,
            "resenia" to resenia
        )
        database.child(videojuegoId.toString()).updateChildren(datosActualizados)
            .addOnSuccessListener {
                _transaccionCompletada.value = true
            }
            .addOnFailureListener {
                _error.value = "Error al actualizar los datos"
            }
    }/*
    fun eliminar(videojuegoId: String){
        database.child(videojuegoId).removeValue()
            .addOnSuccessListener { Toast.makeText(context,
                "Elemento eliminado", Toast.LENGTH_SHORT).show() }
    }*/
}