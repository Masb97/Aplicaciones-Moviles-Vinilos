package com.movilesuniandes.vinilos.features.artists.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import kotlin.math.pow

class ArtistRepositoryImpl : ArtistRepository {

    private val api = RetrofitClient.apiService

    override suspend fun getArtists(): List<Artist> {
        val (musicians, bands) = withContext(Dispatchers.IO) {
            val m = api.getArtists()
            val b = api.getBands()
            Pair(m, b)
        }

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

    override suspend fun getArtistDetail(id: Int, kind: ArtistKind): Artist {
        return retryWithBackoff {
            val dto = withContext(Dispatchers.IO) {
                if (kind == ArtistKind.MUSICO) {
                    api.getMusicianDetail(id)
                } else {
                    api.getBandDetail(id)
                }
            }
            Artist(
                id = dto.id,
                name = dto.name,
                image = dto.image,
                description = dto.description,
                creationDate = dto.creationDate,
                birthDate = dto.birthDate,
                kind = kind
            )
        }
    }

    private suspend fun <T> retryWithBackoff(
        maxRetries: Int = 3,
        baseDelay: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentRetry = 0
        while (true) {
            try {
                return block()
            } catch (e: Exception) {
                if (currentRetry >= maxRetries) {
                    throw e
                }
                val delayTime = (baseDelay * factor.pow(currentRetry.toDouble())).toLong()
                delay(delayTime)
                currentRetry++
            }
        }
    }
}
