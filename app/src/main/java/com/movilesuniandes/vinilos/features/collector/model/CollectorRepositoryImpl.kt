package com.movilesuniandes.vinilos.features.collector.model

import com.movilesuniandes.vinilos.core.remote.RetrofitClient

class CollectorRepositoryImpl : CollectorRepository {

    private val api = RetrofitClient.apiService

    override suspend fun getCollectors(): List<Collector> {
        return api.getCollectors().map { dto ->
            Collector(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                telephone = dto.telephone
            )
        }
    }

    override suspend fun getCollectorDetail(id: Int): CollectorDetail {
        val dto = api.getCollectorDetail(id)
        return CollectorDetail(
            id = dto.id,
            name = dto.name,
            telephone = dto.telephone,
            email = dto.email,
            comments = dto.comments.orEmpty().map { c ->
                CollectorComment(id = c.id, description = c.description, rating = c.rating)
            },
            favoritePerformers = dto.favoritePerformers.orEmpty().map { p ->
                CollectorPerformer(
                    id = p.id,
                    name = p.name,
                    image = p.image,
                    description = p.description,
                    birthDate = p.birthDate
                )
            },
            collectorAlbums = dto.collectorAlbums.orEmpty().map { a ->
                CollectorAlbum(id = a.id, price = a.price, status = a.status)
            }
        )
    }
}