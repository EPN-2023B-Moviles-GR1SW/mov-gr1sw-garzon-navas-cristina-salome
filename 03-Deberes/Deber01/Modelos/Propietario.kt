package Modelos

import java.text.SimpleDateFormat
import java.util.Date
import Archivos.PropietarioArchivos


class Propietario(
    var id: Int,
    var nombre: String,
    var edad: Int,
    var tieneMascotas: Boolean,
    var fechaRegistro: Date
) {
    private val propietarioArchivos = PropietarioArchivos(System.getProperty("user.dir")+ "\\Deber01\\Datos\\PropietarioData.txt")

    fun guardarPropietario() {
        propietarioArchivos.guardarPropietario(this)
        println("Guardando propietario: $nombre")
    }

    fun obtenerPropietario(id: Int): Propietario {
        return propietarioArchivos.obtenerPropietario(id)
    }

    fun obtenerPropietarios(): List<Propietario> {
        return propietarioArchivos.obtenerPropietarios()
    }

    fun actualizarPropietario() {
        propietarioArchivos.actualizarPropietario(this)
        println("Actualizando propietario: $nombre")
    }

    fun eliminarPropietario() {
        propietarioArchivos.eliminarPropietario(this.id)
        println("Eliminando propietario: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Propietario(id=$id, nombre='$nombre', edad=$edad, tieneMascotas=$tieneMascotas, fechaRegistro=${dateFormat.format(fechaRegistro)})"
    }
}
