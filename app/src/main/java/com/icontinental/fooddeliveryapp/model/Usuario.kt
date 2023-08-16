package com.icontinental.fooddeliveryapp.model

data class Usuario(
    val nombres: String? = "",
    val apellidos: String? = "",
    val email: String? = "",
    val direccion: String? = "",
    val latitud: String? = "0.0",
    val longitud: String? = "0.0"
) {

}