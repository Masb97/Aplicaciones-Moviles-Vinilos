package com.movilesuniandes.vinilos.features.collector.model

data class CollectorDetail(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<CollectorComment>,
    val favoritePerformers: List<CollectorPerformer>,
    val collectorAlbums: List<CollectorAlbum>
)
