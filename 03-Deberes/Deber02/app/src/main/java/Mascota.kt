package com.example.deber02

import java.text.SimpleDateFormat
import java.util.Date

class Mascota(
    val id: Int,
    val comentario: String,
    val calificacion: Int,
    val recomendado: Boolean,
    val fechaPublicacion: Date
) {
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        // Formatea la fecha a una cadena en el formato especificado
        val fechaFormateada = formatoFecha.format(fechaPublicacion)

        return "Resenia:\nID:$id\nComentario:$comentario\nCalificación:$calificacion\n¿Es recomendado?:$recomendado\nFechaPublicación:$fechaFormateada"
    }
}