package com.movilesuniandes.vinilos.features.collector

import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorAlbum
import com.movilesuniandes.vinilos.features.collector.model.CollectorComment
import com.movilesuniandes.vinilos.features.collector.model.CollectorDetail
import com.movilesuniandes.vinilos.features.collector.model.CollectorPerformer
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository

class FakeCollectorRepository: CollectorRepository{
    override suspend fun getCollectors(): List<Collector> {
        return listOf(
            Collector(1,"Manolo Bellon", "3502457896", "manollo@carcol.com.co"),
            Collector(2, "Jaime Monsalve", "3012357936", "jmonsalve@rtv.com.co")
        )
    }

    override suspend fun getCollectorDetail(id: Int): CollectorDetail {
        return CollectorDetail(
            id = id,
            name = "Manolo Bellon",
            telephone = "3502457896",
            email = "manollo@carcol.com.co",
            comments = listOf(CollectorComment(1, "Excelente colección", 5)),
            favoritePerformers = listOf(
                CollectorPerformer(
                    id = 1,
                    name = "The Beatles",
                    image = "https://example.com/beatles.jpg",
                    description = "Banda inglesa",
                    birthDate = null
                )
            ),
            collectorAlbums = listOf(CollectorAlbum(1, 100000.0, "Owned"))
        )
    }

}

class FakeCollectorRepositoryWithError : CollectorRepository{
    override suspend fun getCollectors(): List<Collector> {
        throw Exception("Error de conexion")
    }

    override suspend fun getCollectorDetail(id: Int): CollectorDetail {
        throw Exception("Error de conexion")
    }
}