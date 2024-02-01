package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber02.modelo.BaseDatos
import java.util.Date

class EditarPropietario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_propietario)

        val id = findViewById<EditText>(R.id.inp_editarIdProducto)
        val nombre = findViewById<EditText>(R.id.inp_editarNombre)
        val desc = findViewById<EditText>(R.id.inp_editarDesc)
        val precio = findViewById<EditText>(R.id.inp_editarPrecio)

        id.setText(BaseDatos.productoElegido.id.toString())
        nombre.setText(BaseDatos.productoElegido.nombre)
        desc.setText(BaseDatos.productoElegido.descripcion)
        precio.setText(BaseDatos.productoElegido.precio.toString())

        val btnEditarProducto = findViewById<Button>(R.id.btn_editar_prodexist)
        btnEditarProducto.setOnClickListener{
            editarProducto()
            irActividad(ListViewPropietario::class.java)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_editarprod)
        btnCancelar.setOnClickListener {
            irActividad(ListViewPropietario::class.java)
        }


    }

    fun editarProducto() {
        val id = findViewById<EditText>(R.id.inp_editarIdProducto)
        val nombre = findViewById<EditText>(R.id.inp_editarNombre)
        val desc = findViewById<EditText>(R.id.inp_editarDesc)
        val precio = findViewById<EditText>(R.id.inp_editarPrecio)

        BaseDatos.tablaProducto!!.actualizarProducto(
            id.text.toString().toInt(),
            nombre.text.toString(),
            desc.text.toString(),
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