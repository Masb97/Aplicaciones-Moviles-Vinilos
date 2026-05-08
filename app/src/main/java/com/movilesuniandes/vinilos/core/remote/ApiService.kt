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

    @GET("musicians/{id}")
    suspend fun getMusicianDetail(@Path("id") id: Int): ArtistDto

    @GET("bands/{id}")
    suspend fun getBandDetail(@Path("id") id: Int): ArtistDto
}
