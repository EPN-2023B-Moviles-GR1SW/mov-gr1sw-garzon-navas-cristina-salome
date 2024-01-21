package com.example.examenb1_csgn

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
import androidx.appcompat.app.AlertDialog
import com.example.examenb1_csgn.R
import com.example.examenb1_csgn.dao.PropietarioDAO
import com.example.examenb1_csgn.model.Propietario
import com.google.android.material.snackbar.Snackbar

class PropietarioVer : AppCompatActivity() {
    val arregloPopietarios = PropietarioDAO().getAll()
    var posicionItemSeleccionado = 0
    var idPropietarioSeleccionado = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Propietario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_propietario_ver)
        listView = findViewById<ListView>(R.id.lv_propietario_ver)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloPopietarios
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearPropietario = findViewById<Button>(R.id.btn_crear_propietario)
        botonCrearPropietario.setOnClickListener {
            crearPropietario()
        }

        registerForContextMenu(listView)
    }

    fun crearPropietario() {
        val propietario = Propietario(
            1,
            "Nuevo Propeitario",
            21,
            true,
            2021
        )
        PropietarioDAO().create(propietario)
        adaptador.notifyDataSetChanged()
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
        posicionItemSeleccionado = posicion

        val propietarioSeleccionado = arregloPopietarios.get(posicion)
        idPropietarioSeleccionado = propietarioSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mn_editar_prop -> {
                irActividadConId(PropietarioEditar::class.java, idPropietarioSeleccionado)
                return true
            }

            R.id.mn_eliminar_prop -> {
                abrirDialogo()
                return true
            }

            R.id.mn_ver_mascotas -> {
                irActividadConId(MascotaVer::class.java, idPropietarioSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividadConId(
        clase: Class<*>,
        id: Int
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_propietario_ver),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            PropietarioDAO().deleteById(idPropietarioSeleccionado)
            mostrarSnackbar("Elemento id:$idPropietarioSeleccionado eliminado")
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }
}