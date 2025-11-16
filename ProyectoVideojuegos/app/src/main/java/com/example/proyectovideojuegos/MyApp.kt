package com.example.proyectovideojuegos

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
    }
}