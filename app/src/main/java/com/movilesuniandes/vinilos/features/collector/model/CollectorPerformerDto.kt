package com.movilesuniandes.vinilos.features.collector.model

data class CollectorPerformerDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String?
)
