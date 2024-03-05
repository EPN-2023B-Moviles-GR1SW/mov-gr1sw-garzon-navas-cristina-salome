package com.example.examenii.model

import java.text.SimpleDateFormat
import java.util.Date


class Resenia(
    val id: String,
    val comentario: String,
    val calificacion: Long,
    val recomendado: Boolean,
    val fechaPublicacion: Date,
    val idProducto: String? = null
) {
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        // Formatea la fecha a una cadena en el formato especificado
        val fechaFormateada = formatoFecha.format(fechaPublicacion)

        return "Resenia:\nID:$id\nComentario:$comentario\nCalificación:$calificacion\n¿Es recomendado?:$recomendado\nFechaPublicación:$fechaFormateada"
    }
}