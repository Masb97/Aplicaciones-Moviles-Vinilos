package com.movilesuniandes.vinilos.features.albums.model

interface AlbumRepository {
    suspend fun getAlbums(): List<Album>
}
