package com.example.examenb1_csgn.model

class Propietario(
    var id: Int?,
    var nombreP: String,
    var edadP: Int,
    var tieneMascotas: Boolean,
    var anioRegistro: Int,
    var listaMascotas: ArrayList<Mascota> = arrayListOf()
) {

    //Constructor que inicializa los atributos con valores predeterminados.
    constructor() : this(null, "", 0, false, 0)

    override fun toString(): String {
        return "$nombreP"
    }
}