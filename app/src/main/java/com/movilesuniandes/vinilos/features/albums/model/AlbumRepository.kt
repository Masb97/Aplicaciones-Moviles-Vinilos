package com.movilesuniandes.vinilos.features.albums.model

interface AlbumRepository {
    suspend fun getAlbums(): List<Album>
    suspend fun getAlbumById(id: Int): Album
}
