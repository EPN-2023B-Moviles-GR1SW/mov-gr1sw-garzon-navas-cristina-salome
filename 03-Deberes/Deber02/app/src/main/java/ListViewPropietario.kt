package com.example.deber02

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.deber02.modelo.BaseDatos
import com.example.deber02.modelo.SqliteHelperMascota
import com.example.deber02.modelo.SqliteHelperPropietario
import com.google.android.material.snackbar.Snackbar

class ListViewPropietario : AppCompatActivity() {
    var arregloPropietario: MutableList<Propietario>? = null
    var posicionItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_propietario)
        BaseDatos.tablaPropietario = SqliteHelperPropietario(this)
        BaseDatos.tablaMascota = SqliteHelperMascota(this)


        cargarProductos()
        val btnAnadirListView = findViewById<Button>( R.id.btn_crear_prod)
        btnAnadirListView.setOnClickListener{
            agregarPropietario()
        }
        val listView = findViewById<ListView>(R.id.lv_producto)

        registerForContextMenu(listView)

    }

    private fun cargarProductos(){
        val listView = findViewById<ListView>(R.id.lv_producto)
        arregloPropietario = BaseDatos.tablaPropietario!!.getProductos()
        if (arregloPropietario != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arregloPropietario!!
            )
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            registerForContextMenu(listView)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_propietario, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItem = posicion
        mostrarSnackbar("${posicion}")
        BaseDatos.propietarioElegido = arregloPropietario!![posicion]
        BaseDatos.propietarioElegido.mascota = BaseDatos.tablaMascota?.getReseniasDeProducto(BaseDatos.propietarioElegido.id)!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mn_editar_prod ->{
                mostrarSnackbar("${posicionItem}")
                irActividad(EditarPropietario::class.java)
                return true
            }
            R.id.mn_eliminar_prod -> {
                mostrarSnackbar("${posicionItem}")
                abrirDialogo()
                return true
            }
            R.id.mn_ver_resenias -> {
                mostrarSnackbar("${posicionItem}")
                irActividad(ListViewMascota::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)

        }
    }

    fun agregarPropietario() {
        irActividad(CrearPropietario:: class.java)
        cargarProductos()
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                mostrarSnackbar("Acepto ${which}")
                eliminarProducto()

            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarProducto (){
        BaseDatos.tablaPropietario!!.eliminarProducto(BaseDatos.propietarioElegido.id)
        cargarProductos()
    }
    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(findViewById(R.id.lv_producto),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
