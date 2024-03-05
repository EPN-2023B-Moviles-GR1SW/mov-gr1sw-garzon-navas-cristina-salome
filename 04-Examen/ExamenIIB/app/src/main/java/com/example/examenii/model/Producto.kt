package com.example.examenii.model

import com.example.examenib.model.Resenia
import java.text.SimpleDateFormat
import java.util.Date

class Producto(
    var id: String,
    var nombre: String,
    var descripcion: String,
    var precio: Long,
    var fechaCreacion: Date,
    var resenia: MutableList<Resenia>? = null
) {
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        // Formatea la fecha a una cadena en el formato especificado
        val fechaFormateada = formatoFecha.format(fechaCreacion)

        return "Producto:\nID:$id\nNombre:$nombre\nDescripcion:$descripcion\nPrecio:$precio\nFechaCreacion:$fechaFormateada"
    }
}