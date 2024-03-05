package com.example.examenii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenib.databasefrs.FirestoreProducto
import com.example.examenib.model.Producto
import java.util.Date

class EditarPropietario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_propietario)

        val id = findViewById<EditText>(R.id.inp_editarIdProducto)
        val nombre = findViewById<EditText>(R.id.inp_editarNombre)
        val desc = findViewById<EditText>(R.id.inp_editarDesc)
        val precio = findViewById<EditText>(R.id.inp_editarPrecio)

        var idProductoSeleccionado = intent.getStringExtra("idProducto");

        FirestoreProducto.consultarProducto(idProductoSeleccionado!!){
            id.setText(it.id)
            nombre.setText(it.nombre)
            desc.setText(it.descripcion)
            precio.setText(it.precio.toString())
        }


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

        val productoActualizado = Producto(id.text.toString(),
            nombre.text.toString(),
            desc.text.toString(),
            precio.text.toString().toLong(),
            Date())
        FirestoreProducto.actualizarProductos(productoActualizado)

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}