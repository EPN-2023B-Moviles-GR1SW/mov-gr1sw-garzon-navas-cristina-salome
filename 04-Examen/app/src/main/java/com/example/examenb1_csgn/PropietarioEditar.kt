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

        // Obtiene referencias a los elementos visuales en el diseño
        val nombreP = findViewById<EditText>(R.id.input_nombre)
        val edadP = findViewById<EditText>(R.id.input_edad)
        val anioRegistro = findViewById<EditText>(R.id.input_a_registro)
        val tieneMascotas = findViewById<Switch>(R.id.input_tiene_mascotas)

        // Establece los valores del propietario en los elementos visuales
        nombreP.setText(propietario.nombreP)
        edadP.setText(propietario.edadP.toString())
        anioRegistro.setText(propietario.anioRegistro.toString())
        tieneMascotas.isChecked = propietario.tieneMascotas

        // Configura el botón de actualizar para guardar los cambios en el propietario
        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_propietario)
        botonActualizar.setOnClickListener {
            propietario.nombreP = nombreP.text.toString()
            propietario.edadP = edadP.text.toString().toIntOrNull() ?: 0
            propietario.anioRegistro = anioRegistro.text.toString().toIntOrNull() ?: 0
            propietario.tieneMascotas = tieneMascotas.isChecked

            // Actualiza la mascota en la base de datos
            PropietarioDAO().update(propietario)
            mostrarSnackbar("Propietario Actualizado")
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