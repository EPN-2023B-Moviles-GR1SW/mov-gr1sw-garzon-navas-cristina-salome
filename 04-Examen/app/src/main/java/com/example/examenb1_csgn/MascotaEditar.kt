package com.example.examenb1_csgn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examenb1_csgn.R
import com.example.examenb1_csgn.dao.MascotaDAO
import com.google.android.material.snackbar.Snackbar

class MascotaEditar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_editar)

        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Mascota
        val mascota = MascotaDAO().getById(id)!!

        // Setear el texto en componentes visuales
        val nombreM = findViewById<EditText>(R.id.input_mascota)
        val raza = findViewById<EditText>(R.id.input_raza)
        val edadM = findViewById<EditText>(R.id.input_edadM)
        val pesoM = findViewById<EditText>(R.id.input_pesoM)
        val conDuenio = findViewById<Switch>(R.id.input_estado)

        nombreM.setText(mascota.nombreM)
        raza.setText(mascota.raza)
        edadM.setText(mascota.edadM.toString())
        pesoM.setText(mascota.pesoM.toString())
        conDuenio.isChecked = (mascota.conDuenio == "SI")

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_m)
        botonActualizar.setOnClickListener {
            mascota.nombreM = nombreM.text.toString()
            mascota.raza = raza.text.toString()
            mascota.edadM = edadM.text.toString().toInt()
            mascota.pesoM = pesoM.text.toString().toDouble()
            mascota.conDuenio = if (conDuenio.isChecked) "SI" else "NO"

            MascotaDAO().update(mascota)
            mostrarSnackbar("Mascota Actualizada")
        }
        val botonVolver = findViewById<Button>(R.id.btn_volver_m)
        botonVolver.setOnClickListener {
            finish()
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_mascota_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}