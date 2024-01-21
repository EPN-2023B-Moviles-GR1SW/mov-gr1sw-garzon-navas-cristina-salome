package com.example.examenb1_csgn


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examenb1_csgn.R
import com.example.examenb1_csgn.dao.PropietarioDAO
import com.google.android.material.snackbar.Snackbar

class PropietarioEditar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_propietario_editar)

        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Propietario
        val propietario = PropietarioDAO().getById(id)!!

        // Setear el texto en componentes visuales
        val nombreP = findViewById<EditText>(R.id.input_nombre)
        val edadP = findViewById<EditText>(R.id.input_edad)
        val anioRegistro = findViewById<EditText>(R.id.input_a_registro)
        val tieneMascotas = findViewById<Switch>(R.id.input_tiene_mascotas)

        nombreP.setText(propietario.nombreP)
        edadP.setText(propietario.edadP)
        anioRegistro.setText(propietario.anioRegistro.toString())
        tieneMascotas.isChecked = (propietario.tieneMascotas)

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_propietario)
        botonActualizar.setOnClickListener {
            propietario.nombreP = nombreP.text.toString()
            propietario.edadP = edadP.text.toString().toInt()
            propietario.anioRegistro = anioRegistro.text.toString().toInt()
            propietario.tieneMascotas = (tieneMascotas.isChecked)

            PropietarioDAO().update(propietario)
            mostrarSnackbar("Propietario Actualizado")
        }
        val botonVolver = findViewById<Button>(R.id.btn_volver)
        botonVolver.setOnClickListener {
            finish()
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_propietario_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}