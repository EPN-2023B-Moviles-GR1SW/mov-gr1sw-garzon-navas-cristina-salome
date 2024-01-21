package com.example.examenb1_csgn

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    // Llamada a la función "irActividad" para iniciar la actividad PropietarioVer.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        irActividad(PropietarioVer::class.java)

    }
    fun irActividad(
        clase: Class<*>
    ){
        // Se crea un intent para iniciar la actividad especificada.
        val intent = Intent(this, clase)
        // Se inicia la nueva actividad utilizando el intent.
        startActivity(intent)
    }
}