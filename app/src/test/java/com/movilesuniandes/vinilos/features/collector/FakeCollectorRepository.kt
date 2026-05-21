package com.movilesuniandes.vinilos.features.collector

import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorAlbum
import com.movilesuniandes.vinilos.features.collector.model.CollectorComment
import com.movilesuniandes.vinilos.features.collector.model.CollectorDetail
import com.movilesuniandes.vinilos.features.collector.model.CollectorPerformer
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository

class FakeCollectorRepository : CollectorRepository {
    override suspend fun getCollectors(): List<Collector> {
        return listOf(
            Collector(1, "Manolo Bellon", "3502457896", "manollo@carcol.com.co"),
            Collector(2, "Jaime Monsalve", "3012357936", "jmonsalve@rtv.com.co")
        )
    }

    override suspend fun getCollectorDetail(id: Int): CollectorDetail {
        return CollectorDetail(
            id = 1,
            name = "Manolo Bellon",
            telephone = "3502457896",
            email = "manollo@caracol.com.co",
            comments = listOf(
                CollectorComment(id = 1, description = "Great album", rating = 5)
            ),
            favoritePerformers = listOf(
                CollectorPerformer(
                    id = 1,
                    name = "Rubén Blades",
                    image = "https://example.com/image.jpg",
                    description = "Cantante panameño",
                    birthDate = "1948-07-16T00:00:00.000Z"
                )
            ),
            collectorAlbums = listOf(
                CollectorAlbum(id = 1, price = 35.0, status = "Active")
            )
        )
    }
}

class FakeCollectorRepositoryWithError : CollectorRepository {
    override suspend fun getCollectors(): List<Collector> {
        throw Exception("Error de conexion")
    }

    override suspend fun getCollectorDetail(id: Int): CollectorDetail {
        throw Exception("Error de conexion")
    }
}