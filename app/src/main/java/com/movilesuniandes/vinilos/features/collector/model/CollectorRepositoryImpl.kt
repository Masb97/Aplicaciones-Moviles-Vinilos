package com.movilesuniandes.vinilos.features.collector.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectorRepositoryImpl: CollectorRepository {
    private val api = RetrofitClient.apiService

    override suspend fun getCollectors(): List<Collector> {
        return withContext(Dispatchers.IO) {
            api.getCollectors().map { dto ->
                Collector(
                    id = dto.id,
                    name = dto.name,
                    email = dto.email,
                    telephone = dto.telephone
                )
            }
        }
    }
}