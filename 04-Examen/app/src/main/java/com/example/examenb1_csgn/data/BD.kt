package com.example.examen01.bd


import com.example.examenb1_csgn.model.Mascota
import com.example.examenb1_csgn.model.Propietario

class BD {

    companion object {
        var listaDePropietarios: ArrayList<Propietario> = arrayListOf()
        var listaDeMascotas: ArrayList<Mascota> = arrayListOf()

        init {
            listaDeMascotas.add(
                Mascota(
                    0,
                    "Dulce",
                    "Golden",
                    8,
                    12.5,
                    "SI"
                )
            )
            listaDeMascotas.add(
                Mascota(
                    1,
                    "Rambo",
                    "Pastor Aleman",
                    8,
                    12.5,
                    "SI"
                )
            )
            listaDeMascotas.add(
                Mascota(
                    2,
                    "Candy",
                    "French",
                    5,
                    10.0,
                    "SI"
                )
            )

            listaDePropietarios.add(
                Propietario(
                    0,
                    "Camila Perez",
                    21,
                    true,
                    2019
                )
            )
            listaDePropietarios.add(
                Propietario(
                    1,
                    "Carlos Suarez",
                    27,
                    true,
                    2021
                )
            )
            listaDePropietarios.get(0).listaMascotas.add(listaDeMascotas.get(0))
            listaDePropietarios.get(0).listaMascotas.add(listaDeMascotas.get(1))
            listaDePropietarios.get(1).listaMascotas.add(listaDeMascotas.get(2))
            listaDeMascotas.get(0).propietario = listaDePropietarios.get(0)
            listaDeMascotas.get(1).propietario = listaDePropietarios.get(0)
            listaDeMascotas.get(2).propietario = listaDePropietarios.get(1)
        }
    }
}