package com.movilesuniandes.vinilos.features.albums.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient

class AlbumRepositoryImpl : AlbumRepository {

    private val api = RetrofitClient.apiService

    override suspend fun getAlbums(): List<Album> {
        return api.getAlbums().map { dto ->
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
