package com.movilesuniandes.vinilos.features.collector.model

data class CollectorDetailDto(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<CollectorCommentDto>?,
    val favoritePerformers: List<CollectorPerformerDto>?,
    val collectorAlbums: List<CollectorAlbumDto>?
)
