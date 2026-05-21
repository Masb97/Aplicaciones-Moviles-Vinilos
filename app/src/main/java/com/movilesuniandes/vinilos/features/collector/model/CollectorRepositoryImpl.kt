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

    override suspend fun getCollectorDetail(id: Int): CollectorDetail {
        return withContext(Dispatchers.IO) {
            val dto = api.getCollectorDetail(id)
            CollectorDetail(
                id = dto.id,
                name = dto.name,
                telephone = dto.telephone,
                email = dto.email,
                comments = dto.comments.orEmpty().map { comment ->
                    CollectorComment(
                        id = comment.id,
                        description = comment.description,
                        rating = comment.rating
                    )
                },
                favoritePerformers = dto.favoritePerformers.orEmpty().map { performer ->
                    CollectorPerformer(
                        id = performer.id,
                        name = performer.name,
                        image = performer.image,
                        description = performer.description,
                        birthDate = performer.birthDate
                    )
                },
                collectorAlbums = dto.collectorAlbums.orEmpty().map { album ->
                    CollectorAlbum(
                        id = album.id,
                        price = album.price,
                        status = album.status
                    )
                }
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