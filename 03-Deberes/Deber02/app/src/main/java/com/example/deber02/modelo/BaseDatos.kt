package com.example.deber02.modelo

import com.example.deber02.Mascota
import com.example.deber02.Propietario
import java.util.Date

class BaseDatos {
    companion object {
        var tablaProducto: SqliteHelperPropietario ?= null
        var tablaResenia: SqliteHelperMascota ? = null
        var productoElegido= Propietario(0,"default","defaul",0.00,Date(), arrayListOf())
        var reseniaElegida = Mascota(1,"", 0, true, Date())
        var productoSeleccionadoId: Int? = null

        // Método para seleccionar un producto
        fun seleccionarProducto(id: Int) {
            productoSeleccionadoId = id
        }


    }
}