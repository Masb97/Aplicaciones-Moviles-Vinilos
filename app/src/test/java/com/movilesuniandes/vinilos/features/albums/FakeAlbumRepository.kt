package com.movilesuniandes.vinilos.features.albums

import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository

class FakeAlbumRepository : AlbumRepository {
    override suspend fun getAlbums(): List<Album> {
        return listOf(
            Album(1, "Kind of Blue", "", "1959-08-17", "Jazz modal", "Jazz", "Columbia"),
            Album(2, "The Dark Side of the Moon", "", "1973-03-01", "Rock progresivo", "Rock", "Harvest")
        )
    }
}

class FakeAlbumRepositoryWithError : AlbumRepository {
    override suspend fun getAlbums(): List<Album> {
        throw Exception("Error de conexión")
    }
}
