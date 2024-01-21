package Modelos


import java.text.SimpleDateFormat
import java.util.Date
import Archivos.MascotaArchivos

class Mascota(
    var id: Int,
    var nombre: String,
    var raza: String, 
    var edad: Int,
    var peso: Double,
    var propietarioId: Int,
    var nombrePropietario: String, 
    var fechaRegistro: Date
) {
    private val mascotaArchivos = MascotaArchivos(System.getProperty("user.dir")+ "\\Deber01\\Datos\\MacotaData.txt")

    constructor(
        id: Int,
        nombre: String,
        raza: String,
        edad: Int,
        peso: Double,
        propietarioId: Int,
        nombrePropietario: String,
        fechaRegistro: String
    ) : this(
        id,
        nombre,
        raza,
        edad,
        peso,
        propietarioId,
        nombrePropietario,
        SimpleDateFormat("dd/MM/yyyy").parse(fechaRegistro)
    )

    fun guardarMascota() {
        mascotaArchivos.guardarMascota(this)
        println("Guardando mascota: $nombre")
    }

    fun obtenerMascota(id: Int): Mascota {
        return mascotaArchivos.obtenerMascota(id)
    }

    fun obtenerMascotas(): List<Mascota> {
        return mascotaArchivos.obtenerMascotas()
    }

    fun actualizarMascota() {
        mascotaArchivos.actualizarMascota(this)
        println("Actualizando mascota: $nombre")
    }

    fun eliminarMascota() {
        mascotaArchivos.eliminarMascota(this.id)
        println("Eliminando mascota: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Mascota(id=$id, nombre='$nombre', raza='$raza', edad=$edad, peso=$peso, propietarioId=$propietarioId, nombrePropietario='$nombrePropietario', fechaRegistro=${dateFormat.format(fechaRegistro)})"
    }
}
