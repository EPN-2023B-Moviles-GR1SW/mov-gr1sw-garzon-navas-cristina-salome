package com.example.examenb1_csgn.model

class Mascota(
    var id: Int?,
    var nombreM: String,
    var raza: String,
    var edadM: Int,
    var pesoM: Double,
    var conDuenio: String,
    var propietario: Propietario = Propietario()
) {
    constructor() : this(null, "", "", 0, 0.0, "")

    override fun toString(): String {
        return "$nombreM - $raza"
    }
}