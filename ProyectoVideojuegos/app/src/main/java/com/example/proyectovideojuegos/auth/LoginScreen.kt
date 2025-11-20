package com.example.proyectovideojuegos.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectovideojuegos.models.Usuario
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginScreen: ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val authSte = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = authSte
    private val userRol = MutableLiveData<String>()
    val rolUsuario: LiveData<String> = userRol


    init {
        checkAuthenticated()
    }
    fun checkAuthenticated(){
        if(auth.currentUser == null){
            authSte.value = AuthState.Unauthenticated
        }else{
            authSte.value = AuthState.Authenticated
        }
    }

    fun login(email: String,password: String){

        if(email.isEmpty() || password.isEmpty()){
            authSte.value = AuthState.Error("Tanto el Email como la Contraseña deben estar rellenos")
            return
        }

        authSte.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    authSte.value = AuthState.Authenticated
                }else{
                    authSte.value = AuthState.Error(task.exception?.message?: "Algo ha ido mal")
                }
            }
    }

    fun registro(email: String,password: String){

        if(email.isEmpty() || password.isEmpty()){
            authSte.value = AuthState.Error("Tanto el Email como la Contraseña deben estar rellenos")
            return
        }

        authSte.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    authSte.value = AuthState.Authenticated

                    val usuario = auth.currentUser
                    val uid = usuario?.uid

                    if(uid != null){
                        val database = FirebaseDatabase.getInstance(
                            "https://proyectovideojuegos-fbd46-default-rtdb.europe-west1.firebasedatabase.app"
                        ).reference

                        val datosUsuario = Usuario(
                            uid = uid,
                            email = email,
                            rol = "usuario"
                        )

                        database.child("usuarios").child(uid).setValue(datosUsuario)
                            .addOnSuccessListener {
                                authSte.value = AuthState.Authenticated
                            }
                            .addOnFailureListener {
                                authSte.value = AuthState.Error("Error al guardar al usuario en la BD")
                            }
                    }else{
                        authSte.value = AuthState.Error("No se puedo obtener el uid")
                    }
                }else{
                    authSte.value = AuthState.Error(task.exception?.message?: "Algo ha ido mal")
                }
            }
    }
    fun cerrarSesion(){
        auth.signOut()
        authSte.value = AuthState.Unauthenticated
    }

    //Funcion con la cual se el rol del usuario logeado
    fun cargarRolUsuario(){
        val uid = auth.currentUser?.uid ?: return
        val usuarioLogeado = FirebaseDatabase.getInstance(
            "https://proyectovideojuegos-fbd46-default-rtdb.europe-west1.firebasedatabase.app"
        ).getReference("usuarios").child(uid)
        usuarioLogeado.get().addOnSuccessListener { result ->
            val rol = result.child("rol").getValue(String::class.java)
            userRol.value = rol ?: "usuario"
        }
    }
}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(
        val message : String
    ) : AuthState()
}