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

class CrearMascota : AppCompatActivity() {

    var radioT:RadioButton?=null
    var radioF:RadioButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)
        var idProducto = intent.getStringExtra("idProducto")

        radioT = findViewById(R.id.rdb_recom_true_editar)
        radioF = findViewById(R.id.rdb_recom_false_editar)
        val btnCrearNuevaResenia = findViewById<Button>(R.id.btn_crear_nuevares)
        btnCrearNuevaResenia.setOnClickListener{
            crearNuevaResenia()
            irActividad(ListViewMascota::class.java, idProducto!!)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_crearres)
        btnCancelar.setOnClickListener{irActividad(ListViewPropietario:: class.java,idProducto!!)}
    }

    fun crearNuevaResenia(){
        val id = findViewById<EditText>(R.id.inp_idResenia)
        val comentario = findViewById<EditText>(R.id.inp_comentario)
        val calificacion = findViewById<EditText>(R.id.inp_calificacion)
        val recomendado: Boolean = radioT?.isChecked == true
        var idProducto = intent.getStringExtra("idProducto")

        val nuevaResenia = Resenia(id.text.toString(),comentario.text.toString(),calificacion.text.toString().toLong(), recomendado, Date())
        FirestoreResenia.crearResenia(nuevaResenia, idProducto!!)
    }


    fun irActividad (
        clase: Class <*>, idProducto: String
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("idProducto", idProducto)
        startActivity(intent)
    }

}