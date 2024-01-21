package com.example.examenb1_csgn.dao

import com.example.examen01.bd.BD
import com.example.examenb1_csgn.model.Mascota
import com.example.examenb1_csgn.model.Propietario

class PropietarioDAO {

    fun getById(id: Int): Propietario? {
        var propietarioEncontrado: Propietario? = null
        getAll().forEach { propietario: Propietario ->
            if (propietario.id == id) propietarioEncontrado = propietario
        }
        return propietarioEncontrado
    }

    fun getAll(): ArrayList<Propietario> {
        return BD.listaDePropietarios
    }

    fun create(propietario: Propietario) {
        val listaPropietarios = getAll()
        if (listaPropietarios.isEmpty()) {
            propietario.id = 0
        } else {
            propietario.id = listaPropietarios.last().id?.plus(1)!!
        }
        listaPropietarios.add(propietario)
    }

    fun update(propietarioActualizado: Propietario) {
        val listaPropietarios = getAll()
        listaPropietarios.forEachIndexed { index, propietario ->
            if (propietario.id == propietarioActualizado.id) {
                listaPropietarios[index] = propietarioActualizado
                return
            }
        }
    }

    fun deleteById(id: Int): Boolean {
        val mascotaDAO = MascotaDAO()
        getAll().forEach { propietario: Propietario ->
            if (propietario.id == id) {
                propietario.listaMascotas.forEach { mascota: Mascota ->
                    mascotaDAO.deleteById(mascota.id!!)
                }
            }
        }
        return getAll().removeIf { propietario -> (propietario.id == id) }
    }
}
