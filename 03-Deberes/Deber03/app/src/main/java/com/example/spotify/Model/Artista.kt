package com.example.spotify.Model

data class Artista (

    var nombreArtista: String,
    var imagenArtista: Int,
){
    // Constructor vacio
    constructor(): this("",0)
}
