package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.example.deber02.modelo.BaseDatos
import java.util.Date

class CrearMascota : AppCompatActivity() {

    var radioT:RadioButton?=null
    var radioF:RadioButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)

        radioT = findViewById(R.id.rdb_recom_true_editar)
        radioF = findViewById(R.id.rdb_recom_false_editar)
        val btnCrearNuevaMascota = findViewById<Button>(R.id.btn_crear_nuevamas)
        btnCrearNuevaMascota.setOnClickListener{
            crearNuevaMascota()
            irActividad(ListViewMascota::class.java)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_crearmas)
        btnCancelar.setOnClickListener{irActividad(ListViewPropietario:: class.java)}
    }

    fun crearNuevaMascota(){
        val id = findViewById<EditText>(R.id.inp_idMascota)
        val comentario = findViewById<EditText>(R.id.inp_comentario)
        val calificacion = findViewById<EditText>(R.id.inp_calificacion)
        val recomendado: Boolean = radioT?.isChecked == true


        val nuevaMascota = Mascota(id.text.toString().toInt(),comentario.text.toString(),calificacion.text.toString().toInt(), recomendado, Date())
        BaseDatos.propietarioElegido.mascota.add(nuevaMascota)


        BaseDatos.tablaMascota!!.crearResenia(
            id.text.toString().toInt(),comentario.text.toString(),calificacion.text.toString().toInt(), recomendado, Date() , BaseDatos.propietarioElegido.id
        )
    }


    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}