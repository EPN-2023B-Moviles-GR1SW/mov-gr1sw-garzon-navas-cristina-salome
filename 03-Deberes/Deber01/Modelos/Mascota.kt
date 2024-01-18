package Modelos


import java.text.SimpleDateFormat
import java.util.Date

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
    private val MascotaArchivos = (System.getProperty("user.dir")+ "\\src\\main\\kotlin\\archivos\\Mascotas.txt")

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
        mascotaRepository.guardarMascota(this)
        println("Guardando mascota: $nombre")
    }

    fun obtenerMascota(id: Int): Mascota {
        return mascotaRepository.obtenerMascota(id)
    }

    fun obtenerMascotas(): List<Mascota> {
        return mascotaRepository.obtenerMascotas()
    }

    fun actualizarMascota() {
        mascotaRepository.actualizarMascota(this)
        println("Actualizando mascota: $nombre")
    }

    fun eliminarMascota() {
        mascotaRepository.eliminarMascota(this.id)
        println("Eliminando mascota: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Mascota(id=$id, nombre='$nombre', raza='$raza', edad=$edad, peso=$peso, propietarioId=$propietarioId, nombrePropietario='$nombrePropietario', fechaRegistro=${dateFormat.format(fechaRegistro)})"
    }
}
