package com.movilesuniandes.vinilos.core.remote

import com.movilesuniandes.vinilos.features.albums.model.AlbumDto
import com.movilesuniandes.vinilos.features.artists.model.ArtistDto
import com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest
import com.movilesuniandes.vinilos.features.collector.model.CollectorDto
import com.movilesuniandes.vinilos.features.collector.model.CollectorDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header

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
  
    @GET("collectors")
    suspend fun getCollectors(): List<CollectorDto>
    
    @GET("collectors/{id}")
    suspend fun getCollectorDetail(@Path("id") id: Int): CollectorDetailDto
  
    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id")id: Int): AlbumDto

    @POST("albums")
    suspend fun createAlbum(
        @Header("Authorization") authorization: String?,
        @Body request: CreateAlbumRequest
    ): AlbumDto
}
