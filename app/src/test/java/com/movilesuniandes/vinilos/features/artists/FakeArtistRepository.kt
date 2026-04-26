package com.movilesuniandes.vinilos.features.artists

import com.movilesuniandes.vinilos.features.artists.model.Artist
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository

class FakeArtistRepository : ArtistRepository {
    override suspend fun getArtists(): List<Artist> {
        return listOf(
            Artist(1, "Rubén Blades", "", "Cantautor", birthDate = "1948-07-16", kind = ArtistKind.MUSICO),
            Artist(2, "Soda Stereo", "", "Banda de rock", creationDate = "1982-01-01", kind = ArtistKind.BANDA)
        )
    }
}

class FakeArtistRepositoryWithError : ArtistRepository {
    override suspend fun getArtists(): List<Artist> {
        throw Exception("Error de conexión")
    }
}
