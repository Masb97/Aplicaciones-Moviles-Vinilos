package com.movilesuniandes.vinilos.core.remote

import com.movilesuniandes.vinilos.features.albums.model.AlbumDto
import com.movilesuniandes.vinilos.features.artists.model.ArtistDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("musicians")
    suspend fun getArtists(): List<ArtistDto>

    @GET("bands")
    suspend fun getBands(): List<ArtistDto>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id")id: Int): AlbumDto
}
