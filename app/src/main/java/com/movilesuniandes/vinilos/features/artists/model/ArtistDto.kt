package com.movilesuniandes.vinilos.features.artists.model

data class ArtistDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String? = null,
    val birthDate: String? = null
)
