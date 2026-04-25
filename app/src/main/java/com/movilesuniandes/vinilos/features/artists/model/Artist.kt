package com.movilesuniandes.vinilos.features.artists.model

enum class ArtistKind {
    MUSICO,
    BANDA
}

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String? = null,
    val birthDate: String? = null,
    val kind: ArtistKind
)
