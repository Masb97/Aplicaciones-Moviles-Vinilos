package com.movilesuniandes.vinilos.features.albums.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepositoryImpl : AlbumRepository {

    private val api = RetrofitClient.apiService

    override suspend fun getAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {
            api.getAlbums().map { dto ->
                Album(
                    id = dto.id,
                    name = dto.name,
                    cover = dto.cover,
                    releaseDate = dto.releaseDate,
                    description = dto.description,
                    genre = dto.genre,
                    recordLabel = dto.recordLabel
                )
            }
        }
    }

    override suspend fun getAlbumById(id: Int): Album {
       return withContext(Dispatchers.IO) {
           val dto = api.getAlbumById(id)
           Album(
               id = dto.id,
               name = dto.name,
               cover = dto.cover,
               releaseDate = dto.releaseDate,
               description = dto.description,
               genre = dto.genre,
               recordLabel = dto.recordLabel
           )
       }
    }

    override suspend fun createAlbum(request: CreateAlbumRequest): Album {
        return withContext(Dispatchers.IO) {
            val dto = api.createAlbum(null, request)
            Album(
                id = dto.id,
                name = dto.name,
                cover = dto.cover,
                releaseDate = dto.releaseDate,
                description = dto.description,
                genre = dto.genre,
                recordLabel = dto.recordLabel
            )
        }
    }
}
