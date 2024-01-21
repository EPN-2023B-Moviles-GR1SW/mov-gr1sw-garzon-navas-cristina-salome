package com.example.examenb1_csgn.model

//Clase mascota con sus respectivos atributos
class Mascota(
    var id: Int?,
    var nombreM: String,
    var raza: String,
    var edadM: Int,
    var pesoM: Double,
    var conDuenio: String,
    var propietario: Propietario = Propietario()
) {
    //Constructor que inicializa los atributos con valores predeterminados.
    constructor() : this(null, "", "", 0, 0.0, "")

    override fun toString(): String {
        return "$nombreM - $raza"
    }
}