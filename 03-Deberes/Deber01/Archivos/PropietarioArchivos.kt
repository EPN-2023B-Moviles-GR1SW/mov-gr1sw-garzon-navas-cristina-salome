package Archivos

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import Modelos.Propietario

class PropietarioRepository(private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarPropietario(propietario: Propietario) {
        val registro = "${propietario.id},${propietario.nombre},${propietario.edad}," +
                "${propietario.tieneMascotas},${dateFormat.format(propietario.fechaRegistro)}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerPropietario(id: Int): Propietario {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró un propietario con el ID $id")

        return Propietario(
            id = campos[0].toInt(),
            nombre = campos[1],
            edad = campos[2].toInt(),
            tieneMascotas = campos[3].toBoolean(),
            fechaRegistro = dateFormat.parse(campos[4])
        )
    }

    fun obtenerPropietarios(): List<Propietario> {
        return File(fileName).readLines().map { line ->
            val campos = line.split(",")
            Propietario(
                id = campos[0].toInt(),
                nombre = campos[1],
                edad = campos[2].toInt(),
                tieneMascotas = campos[3].toBoolean(),
                fechaRegistro = dateFormat.parse(campos[4])
            )
        }
    }

    fun actualizarPropietario(propietario: Propietario) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${propietario.id},")) {
                "${propietario.id},${propietario.nombre},${propietario.edad}," +
                        "${propietario.tieneMascotas},${dateFormat.format(propietario.fechaRegistro)}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun eliminarPropietario(id: Int) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }
}
