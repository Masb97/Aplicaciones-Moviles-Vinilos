package com.movilesuniandes.vinilos.features.albums.model

data class CreateTrack(
    val name: String,
    val duration: String
)

data class CreateAlbumRequest(
    val name: String,
    val cover: String? = null,
    val releaseDate: String,
    val description: String? = null,
    val genre: String,
    val recordLabel: String? = null,
    val performerIds: List<Int>? = null,
    val tracks: List<CreateTrack>? = null
)
