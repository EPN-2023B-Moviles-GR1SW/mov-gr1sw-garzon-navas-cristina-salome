package com.example.deber02.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.Propietario
import java.text.SimpleDateFormat
import java.util.*

class SqliteHelperPropietario(
    contexto: Context,
) : SQLiteOpenHelper(
    contexto,
    "productos",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPropietario =
            """
               CREATE TABLE PRODUCTO (
                   ID INTEGER PRIMARY KEY,
                   NOMBRE VARCHAR(50),
                   DESCRIPCION VARCHAR(100),
                   PRECIO REAL,
                   FECHA_CREACION TEXT
               )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPropietario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearProducto(id:Int,nombre:String,descripcion: String, precio: Double, fechaCreacion: Date): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("ID", id)
        valoresAGuardar.put("NOMBRE", nombre)
        valoresAGuardar.put("DESCRIPCION",descripcion)
        valoresAGuardar.put("PRECIO", precio)
        valoresAGuardar.put("FECHA_CREACION", formatDate(fechaCreacion))
        val resultadoGuardar = basedatosEscritura
            .insert(
                "PROPIETARIO",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun actualizarProducto(id:Int,nombre:String,descripcion: String, precio: Double, fechaCreacion: Date): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("NOMBRE", nombre)
        valoresAActualizar.put("DESCRIPCION", descripcion)
        valoresAActualizar.put("PRECIO", precio)
        valoresAActualizar.put("FECHA_CREACION", formatDate(fechaCreacion))

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = basedatosEscritura
            .update(
                "PROPIETARIO",
                valoresAActualizar,
                "ID=?",
                parametrosConsultaActualizar
            )

        basedatosEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }
    fun eliminarProducto(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PROPIETARIO",
                "ID=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun consultarProductoPorId(id: Int): Propietario? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """SELECT * FROM PRODUCTO WHERE ID = ?""".trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura: Cursor = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val existePropietario = resultadoConsultaLectura.moveToFirst()
        var propietarioEncontrado = Propietario(0,"default","defaul",0.00,Date(),

        )

        if (existePropietario) {
            val id = resultadoConsultaLectura.getInt(0)
            val nombre = resultadoConsultaLectura.getString(1)
            val descripcion = resultadoConsultaLectura.getString(2)
            val precio = resultadoConsultaLectura.getDouble(3)
            val fechaCreacionString = resultadoConsultaLectura.getString(4)
            val fechaCreacion = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaCreacionString)

            propietarioEncontrado = Propietario(id, nombre, descripcion, precio, fechaCreacion ?: Date())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return propietarioEncontrado
    }

    fun getProductos():MutableList<Propietario>{
        val baseDatosLectura = readableDatabase
        val scriptConsulta = """SELECT * FROM PRODUCTO""".trimIndent()
        val resultado = baseDatosLectura.rawQuery(scriptConsulta, null)
        val exist = resultado.moveToFirst()

        val productos = arrayListOf<Propietario>()

        if (exist){
            do {
                val id = resultado.getInt(0)
                val nombre = resultado.getString(1)
                val descripcion = resultado.getString(2)
                val precio = resultado.getDouble(3)
                val fechaCreacionString = resultado.getString(4)
                val fechaCreacion = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaCreacionString)

                if (id!= null){
                    val producto = Propietario(id,nombre,descripcion,precio,fechaCreacion)
                    productos.add(producto)
                }
            } while (resultado.moveToNext())
        }
        resultado.close()
        baseDatosLectura.close()
        return productos
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}
