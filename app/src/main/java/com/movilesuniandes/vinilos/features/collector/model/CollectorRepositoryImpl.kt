package com.movilesuniandes.vinilos.features.collector.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient

class CollectorRepositoryImpl: CollectorRepository {
    private val api= RetrofitClient.apiService

    override suspend fun getCollectors(): List<Collector>{
        return api.getCollectors().map { dto ->
            Collector(
                id= dto.id,
                name= dto.name,
                email = dto.email,
                telephone= dto.telephone
            )

        }
    }
}