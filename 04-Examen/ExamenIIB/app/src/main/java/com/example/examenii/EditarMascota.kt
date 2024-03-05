package com.example.examenii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.example.examenib.databasefrs.FirestoreResenia
import com.example.examenib.model.Resenia
import java.util.Date

class EditarMascota : AppCompatActivity() {

    var radioTrue: RadioButton? = null
    var radioFalse: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        val id = findViewById<EditText>(R.id.inp_editarIdResenia)
        val comentario = findViewById<EditText>(R.id.inp_editarComentario)
        val calificacion = findViewById<EditText>(R.id.inp_editarCalificacion)

        val radioTrue = findViewById<RadioButton>(R.id.rdb_recom_true_editar)
        val radioFalse = findViewById<RadioButton>(R.id.rdb_recom_false_editar)
        var recomendado: Boolean = false

        radioTrue.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                recomendado = true
            }
        }

        radioFalse.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                recomendado = false
            }
        }

        val idResenia = intent.getStringExtra("id")
        FirestoreResenia.consultarResenia(idResenia!!){
            id.setText(it.id)
            comentario.setText(it.comentario)
            calificacion.setText(it.calificacion.toString())
            recomendado = it.recomendado
        }

        val btnEditarResenia = findViewById<Button>(R.id.btn_editarResenia)
        btnEditarResenia.setOnClickListener {
            editarResenia()
            irActividad(ListViewMascota::class.java)
        }
    }

    private fun editarResenia() {
        val id = findViewById<EditText>(R.id.inp_editarIdResenia)
        val comentario = findViewById<EditText>(R.id.inp_editarComentario)
        val calificacion = findViewById<EditText>(R.id.inp_editarCalificacion)
        val recomendado: Boolean = radioTrue?.isChecked == true

        val reseniaEditada = Resenia(
            id.text.toString(),
            comentario.text.toString(),
            calificacion.text.toString().toLong(),
            recomendado,
            Date()
        )

        FirestoreResenia.actualizarResenia(reseniaEditada)

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}