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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.examenb1_csgn.R
import com.example.examenb1_csgn.dao.MascotaDAO
import com.example.examenb1_csgn.dao.PropietarioDAO
import com.example.examenb1_csgn.model.Mascota
import com.example.examenb1_csgn.model.Propietario
import com.google.android.material.snackbar.Snackbar

class MascotaVer : AppCompatActivity() {

    var arregloMascotas = arrayListOf<Mascota>()
    var propietario: Propietario = Propietario()
    var posicionItemSeleccionado = 0
    var idMascotaSeleccionada = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Mascota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotas_ver)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Mascotas
        propietario = PropietarioDAO().getById(id)!!
        arregloMascotas = propietario.listaMascotas

        val nombrePropietario = findViewById<TextView>(R.id.tv_nombre_mascota)
        nombrePropietario.text = "${propietario.nombreP}"

        listView = findViewById<ListView>(R.id.lv_mascota_ver)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // cómo se va a ver (XML)
            arregloMascotas
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearMascota = findViewById<Button>(R.id.btn_crear_mascota)
        botonCrearMascota.setOnClickListener {
            crearMascota()
        }

        registerForContextMenu(listView)
    }
    fun crearMascota() {
        val mascota = Mascota(
            null,
            "Candy",
            "French",
            11,
            10.0,
            "SI",
            propietario
        )
        MascotaDAO().create(mascota)
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_mascota, menu)
        // Obtener el ID del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Mascota en la posición seleccionada
        val mascotaSeleccionada = arregloMascotas[posicion]
        // Obtener el ID de la Película seleccionada
        idMascotaSeleccionada = mascotaSeleccionada.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mn_editar_mas -> {
                irActividadConId(MascotaEditar::class.java, idMascotaSeleccionada)
                return true
            }

            R.id.mn_eliminar_mas -> {
                abrirDialogo()
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
            findViewById(R.id.id_layout_mascota_ver),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            MascotaDAO().deleteById(idMascotaSeleccionada)
            mostrarSnackbar("Elemento id:${idMascotaSeleccionada} eliminado")
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }
}