package com.movilesuniandes.vinilos.features.collector.model

interface CollectorRepository {
    suspend fun getCollectors(): List<Collector>
}