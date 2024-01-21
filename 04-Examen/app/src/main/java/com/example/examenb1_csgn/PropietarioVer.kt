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

    // Lista de propietarios obtenida de la base de datos
    val arregloPopietarios = PropietarioDAO().getAll()

    // Posición del ítem seleccionado en la lista y ID del propietario seleccionado
    var posicionItemSeleccionado = 0
    var idPropietarioSeleccionado = 0

    // Declaración de variables para la lista y su adaptador
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Propietario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establece el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_propietario_ver)

        // Inicializa la lista y su adaptador
        listView = findViewById<ListView>(R.id.lv_propietario_ver)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloPopietarios
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        // Configura el botón para crear un nuevo propietario
        val botonCrearPropietario = findViewById<Button>(R.id.btn_crear_propietario)
        botonCrearPropietario.setOnClickListener {
            crearPropietario()
        }

        // Registra la lista para el menú contextual
        registerForContextMenu(listView)
    }

    // Función para crear un nuevo propietario
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

    // Función llamada al crear el menú contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_propietario, menu)

        // Obtiene información sobre el ítem seleccionado en la lista
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion

        // Obtiene el propietario seleccionado y su ID
        val propietarioSeleccionado = arregloPopietarios.get(posicion)
        idPropietarioSeleccionado = propietarioSeleccionado.id!!
    }

    // Función llamada al seleccionar una opción en el menú contextual
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mn_editar_prop -> {
                // Abre la actividad de edición con el ID del propietario seleccionado
                irActividadConId(PropietarioEditar::class.java, idPropietarioSeleccionado)
                return true
            }

            R.id.mn_eliminar_prop -> {
                abrirDialogo()
                return true
            }

            R.id.mn_ver_mascotas -> {

                // Abre la actividad para ver las mascotas del propietario seleccionado
                irActividadConId(MascotaVer::class.java, idPropietarioSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    // Función para iniciar una actividad con un ID específico
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

    // Función para abrir un diálogo de confirmación para eliminar un propietario
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