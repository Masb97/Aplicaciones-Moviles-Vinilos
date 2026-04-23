package com.movilesuniandes.vinilos.core.remote

import com.movilesuniandes.vinilos.features.albums.model.AlbumDto
import retrofit2.http.GET

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>
}
