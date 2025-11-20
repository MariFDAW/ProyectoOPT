package com.example.proyectovideojuegos.models

data class Videojuegos(
    var videojuegoId: Int = 0, //DEBE ser de autoincremento
    var nombre: String = "",
    var completado: String ="",
    var calificacion: Int = 0,
    var resenia: String = ""
)
