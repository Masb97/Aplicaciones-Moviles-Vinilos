package com.movilesuniandes.vinilos.features.artists.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient

class ArtistRepositoryImpl : ArtistRepository {

    private val api = RetrofitClient.apiService

    override suspend fun getArtists(): List<Artist> {
        val musicians = api.getArtists()
        val bands = api.getBands()

        val musicianArtists = musicians.map { dto ->
            Artist(
                id = dto.id,
                name = dto.name,
                image = dto.image,
                description = dto.description,
                creationDate = dto.creationDate,
                birthDate = dto.birthDate,
                kind = ArtistKind.MUSICO
            )
        }

        val bandArtists = bands.map { dto ->
            Artist(
                id = dto.id,
                name = dto.name,
                image = dto.image,
                description = dto.description,
                creationDate = dto.creationDate,
                birthDate = dto.birthDate,
                kind = ArtistKind.BANDA
            )
        }

        return (musicianArtists + bandArtists)
            .distinctBy { artist -> artist.id to artist.name }
            .sortedBy { artist -> artist.name }
    }
}
