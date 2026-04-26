package com.movilesuniandes.vinilos.features.artists.model

interface ArtistRepository {
    suspend fun getArtists(): List<Artist>
}
