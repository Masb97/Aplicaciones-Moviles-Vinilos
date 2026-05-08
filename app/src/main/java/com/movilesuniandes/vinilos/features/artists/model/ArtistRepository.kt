package com.movilesuniandes.vinilos.features.artists.model

interface ArtistRepository {
    suspend fun getArtists(): List<Artist>
    suspend fun getArtistDetail(id: Int, kind: ArtistKind): Artist
}
