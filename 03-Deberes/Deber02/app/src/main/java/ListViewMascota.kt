package com.example.deber02

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.deber02.modelo.BaseDatos
import com.google.android.material.snackbar.Snackbar


class ListViewMascota : AppCompatActivity() {
    var productoElegido = BaseDatos.productoElegido
    var reseniasProducto = BaseDatos.tablaResenia?.getReseniasDeProducto(BaseDatos.productoElegido.id)
    var posicionItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_mascota)
        cargarResenias()
        val nombreProducto = findViewById<TextView>(R.id.tv_nombre_prod)
        nombreProducto.text = productoElegido.nombre

        val botonVolver = findViewById<Button>(R.id.btn_volver)
        botonVolver.setOnClickListener{
            irActividad(ListViewPropietario :: class.java)
        }

        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_nuevaresenia)
        botonAnadirListView.setOnClickListener{
            agregarResenia()
        }

        val listView = findViewById<ListView>(R.id.lv_resenia)
        registerForContextMenu(listView)

    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mn_editar_res -> {
                mostrarSnackbar("${posicionItem}")
                irActividad(EditarMascota::class.java)
                return true
            }
            R.id.mn_eliminar_res -> {
                mostrarSnackbar("${posicionItem}")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_mascota, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItem = posicion
        mostrarSnackbar("${posicion}")
        BaseDatos.reseniaElegida = productoElegido.mascota!!.get(posicion)
    }

    private fun cargarResenias(){
        val listView = findViewById<ListView>(R.id.lv_resenia)
        reseniasProducto = BaseDatos.tablaResenia!!.getReseniasDeProducto(BaseDatos.productoElegido.id)
        if (reseniasProducto != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                reseniasProducto!!
            )
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            registerForContextMenu(listView)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                eliminarResenia()
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarResenia (){
        BaseDatos.tablaResenia!!.eliminarResenia(BaseDatos.productoElegido.id)
        cargarResenias()
    }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(findViewById(R.id.lv_resenia),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    fun agregarResenia(){
        irActividad(CrearMascota::class.java)
        cargarResenias()
    }

    fun irActividad (
        clase: Class <*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}