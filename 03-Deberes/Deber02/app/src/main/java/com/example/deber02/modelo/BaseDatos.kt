package com.example.deber02.modelo

import com.example.deber02.Mascota
import com.example.deber02.Propietario
import java.util.Date

class BaseDatos {
    companion object {
        var tablaPropietario: SqliteHelperPropietario ?= null
        var tablaMascota: SqliteHelperMascota ? = null
        var propietarioElegido= Propietario(0,"default","defaul",0.00,Date(), arrayListOf())
        var mascotaElegida = Mascota(1,"", 0, true, Date())
        var propietarioSeleccionadoId: Int? = null

        // Método para seleccionar un producto
        fun seleccionarProducto(id: Int) {
            propietarioSeleccionadoId = id
        }


    }
}