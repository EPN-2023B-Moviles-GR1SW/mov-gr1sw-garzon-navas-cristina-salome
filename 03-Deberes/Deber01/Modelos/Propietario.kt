package Modelos

import java.text.SimpleDateFormat
import java.util.Date

class Propietario(
    var id: Int,
    var nombre: String,
    var edad: Int,
    var tieneMascotas: Boolean,
    var fechaRegistro: Date
) {
    private val propietarioRepository = PropietarioArchivos(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\Datos\\Propietarios.txt")

    fun guardarPropietario() {
        propietarioRepository.guardarPropietario(this)
        println("Guardando propietario: $nombre")
    }

    fun obtenerPropietario(id: Int): Propietario {
        return propietarioRepository.obtenerPropietario(id)
    }

    fun obtenerPropietarios(): List<Propietario> {
        return propietarioRepository.obtenerPropietarios()
    }

    fun actualizarPropietario() {
        propietarioRepository.actualizarPropietario(this)
        println("Actualizando propietario: $nombre")
    }

    fun eliminarPropietario() {
        propietarioRepository.eliminarPropietario(this.id)
        println("Eliminando propietario: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Propietario(id=$id, nombre='$nombre', edad=$edad, tieneMascotas=$tieneMascotas, fechaRegistro=${dateFormat.format(fechaRegistro)})"
    }
}
