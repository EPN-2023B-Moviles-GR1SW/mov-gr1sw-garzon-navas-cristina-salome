package com.example.deber02.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.Mascota
import java.text.SimpleDateFormat
import java.util.*

class SqliteHelperMascota(
    contexto: Context,
) : SQLiteOpenHelper(
    contexto,
    "macotas",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaMascota =
            """
               CREATE TABLE MASCOTA (
                   ID INTEGER PRIMARY KEY,
                   COMENTARIO TEXT,
                   PUNTUACION INTEGER,
                   APROBADA INTEGER,
                   FECHA_RESENIA TEXT,
                   PRODUCTO_ID INTEGER,
                   FOREIGN KEY(PRODUCTO_ID) REFERENCES PRODUCTO(ID)
               )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaMascota)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearResenia(id:Int, comentario: String, calificacion: Int, recomendado: Boolean, fechaPublicacion: Date, productoId: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("ID", id)
        valoresAGuardar.put("COMENTARIO", comentario)
        valoresAGuardar.put("PUNTUACION", calificacion)
        valoresAGuardar.put("APROBADA", if (recomendado) 1 else 0)
        valoresAGuardar.put("FECHA_MASCOTA", formatDate(fechaPublicacion))
        valoresAGuardar.put("PRODUCTO_ID", productoId)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "MASCOTA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun actualizarResenia(id:Int, comentario: String, calificacion: Int, recomendado: Boolean, fechaPublicacion: Date, productoId: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("COMENTARIO", comentario)
        valoresAActualizar.put("PUNTUACION", calificacion)
        valoresAActualizar.put("APROBADA", if (recomendado) 1 else 0)
        valoresAActualizar.put("FECHA_MASCOTA", formatDate(fechaPublicacion))

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = basedatosEscritura
            .update(
                "MASCOTA",
                valoresAActualizar,
                "ID=?",
                parametrosConsultaActualizar
            )

        basedatosEscritura.close()
        return resultadoActualizacion > 0
    }

    fun consultarReseniaPorId(id: Int): Mascota? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """SELECT * FROM RESENIA WHERE ID = ?""".trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura: Cursor = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val existeMascota = resultadoConsultaLectura.moveToFirst()
        var mascotaEncontrada = Mascota(0, "", 0, false, Date())

        if (existeMascota) {
            val id = resultadoConsultaLectura.getInt(0)
            val comentario = resultadoConsultaLectura.getString(1)
            val calificacion = resultadoConsultaLectura.getInt(2)
            val recomendado = resultadoConsultaLectura.getInt(3) == 1
            val fechaResenaString = resultadoConsultaLectura.getString(4)
            val fechaResena = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaResenaString)

            mascotaEncontrada = Mascota(id, comentario, calificacion, recomendado, fechaResena ?: Date())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return mascotaEncontrada
    }
    fun eliminarMascota(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "MASCOTA",
                "ID=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    // Método para obtener todas las mascotas de un propietario
    fun getReseniasDeProducto(productoId: Int): MutableList<Mascota> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM MASCOTA WHERE PROPIETARIO_ID = ?"
        val parametrosConsultaLectura = arrayOf(productoId.toString())
        val resultadoConsultaLectura: Cursor = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val mascota = mutableListOf<Mascota>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val comentario = resultadoConsultaLectura.getString(1)
                val calificacion = resultadoConsultaLectura.getInt(2)
                val recomendado = resultadoConsultaLectura.getInt(3) == 1
                val fechaResenaString = resultadoConsultaLectura.getString(4)
                val fechaResena = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaResenaString)

                val mascot = Mascota(id, comentario, calificacion, recomendado, fechaResena ?: Date())
                mascota.add(mascot)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return mascota
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}