package Archivos

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import Modelos.Mascota

class MascotaRepository(private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarMascota(mascota: Mascota) {
        val registro = "${mascota.id},${mascota.nombre},${mascota.raza},${mascota.edad}," +
                "${mascota.peso},${mascota.propietarioId},${mascota.nombrePropietario}," +
                "${dateFormat.format(mascota.fechaRegistro)}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerMascota(id: Int): Mascota {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró una mascota con el ID $id")

        return Mascota(
            id = campos[0].toInt(),
            nombre = campos[1],
            raza = campos[2],
            edad = campos[3].toInt(),
            peso = campos[4].toDouble(),
            propietarioId = campos[5].toInt(),
            nombrePropietario = campos[6],
            fechaRegistro = dateFormat.parse(campos[7])
        )
    }

    fun obtenerMascotas(): List<Mascota> {
        return File(fileName).readLines().map { line ->
            val campos = line.split(",")
            Mascota(
                id = campos[0].toInt(),
                nombre = campos[1],
                raza = campos[2],
                edad = campos[3].toInt(),
                peso = campos[4].toDouble(),
                propietarioId = campos[5].toInt(),
                nombrePropietario = campos[6],
                fechaRegistro = dateFormat.parse(campos[7])
            )
        }
    }

    fun actualizarMascota(mascota: Mascota) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${mascota.id},")) {
                "${mascota.id},${mascota.nombre},${mascota.raza},${mascota.edad}," +
                        "${mascota.peso},${mascota.propietarioId},${mascota.nombrePropietario}," +
                        "${dateFormat.format(mascota.fechaRegistro)}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun eliminarMascota(id: Int) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }
}
