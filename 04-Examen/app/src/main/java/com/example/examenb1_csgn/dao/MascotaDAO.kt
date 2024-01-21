package com.example.examenb1_csgn.dao

import com.example.examen01.bd.BD
import com.example.examenb1_csgn.model.Mascota

class MascotaDAO {

    //Obtiene una mascota por su identificador único.
    fun getById(id: Int): Mascota? {
        var mascotaEncontrada: Mascota? = null
        getAll().forEach { mascota: Mascota ->
            if (mascota.id == id) mascotaEncontrada = mascota
        }
        return mascotaEncontrada
    }

    //Obtiene todas las mascotas de la base de datos.
    fun getAll(): ArrayList<Mascota> {
        return BD.listaDeMascotas
    }

    //Crea una nueva mascota y la agrega a la base de datos.
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

    //Actualiza la información de una mascota existente en la base de datos.
    fun update(mascotaActualizada: Mascota) {
        val listaMascotas = getAll()
        listaMascotas.forEachIndexed { index, mascota ->
            if (mascota.id == mascotaActualizada.id) {
                listaMascotas[index] = mascotaActualizada
                return
            }
        }
    }

    // Elimina una mascota de la base de datos por su identificador único.
    fun deleteById(id: Int) {
        val mascota = getById(id)
        if (mascota != null) {
            mascota.propietario.listaMascotas.remove(mascota)
            getAll().remove(mascota)
        }
    }

}