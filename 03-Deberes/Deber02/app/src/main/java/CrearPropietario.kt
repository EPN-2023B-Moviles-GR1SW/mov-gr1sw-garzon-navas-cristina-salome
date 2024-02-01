package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber02.modelo.BaseDatos
import java.util.Date

class CrearPropietario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_propietario)

        val btnCrearNuevoProducto = findViewById<Button>(R.id.btn_crear_nuevoprop)
        btnCrearNuevoProducto.setOnClickListener {
            crearNuevoProducto()
            irActividad(ListViewPropietario::class.java)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_crearprop)
        btnCancelar.setOnClickListener { irActividad(ListViewPropietario::class.java) }
    }

    fun crearNuevoProducto() {
        val id = findViewById<EditText>(R.id.inp_idPropietario)
        val nombre = findViewById<EditText>(R.id.inp_nombre)
        val descripcion = findViewById<EditText>(R.id.inp_desc)
        val precio = findViewById<EditText>(R.id.inp_precio)

        BaseDatos.tablaPropietario!!.crearProducto(
            id.text.toString().toInt(),
            nombre.text.toString(),
            descripcion.text.toString(),
            precio.text.toString().toDouble(),
            Date()
        )
    }


    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}