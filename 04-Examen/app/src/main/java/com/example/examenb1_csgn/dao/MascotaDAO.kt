package com.example.examenb1_csgn.dao

import com.example.examen01.bd.BD
import com.example.examenb1_csgn.model.Mascota

class MascotaDAO {

    fun getById(id: Int): Mascota? {
        var mascotaEncontrada: Mascota? = null
        getAll().forEach { mascota: Mascota ->
            if (mascota.id == id) mascotaEncontrada = mascota
        }
        return mascotaEncontrada
    }

    fun getAll(): ArrayList<Mascota> {
        return BD.listaDeMascotas
    }

    fun create(mascota: Mascota) {
        val listaMascotas = getAll()
        if (listaMascotas.isEmpty()) {
            mascota.id = 0
        } else {
            mascota.id = listaMascotas.last().id?.plus(1)!!
        }
        listaMascotas.add(mascota)
        mascota.propietario.listaMascotas.add(mascota)
    }

    fun update(mascotaActualizada: Mascota) {
        val listaMascotas = getAll()
        listaMascotas.forEachIndexed { index, mascota ->
            if (mascota.id == mascotaActualizada.id) {
                listaMascotas[index] = mascotaActualizada
                return
            }
        }
    }

    fun deleteById(id: Int) {
        val mascota = getById(id)
        if (mascota != null) {
            mascota.propietario.listaMascotas.remove(mascota)
            getAll().remove(mascota)
        }
    }

}