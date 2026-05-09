package com.movilesuniandes.vinilos.features.collector

import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepositoryImpl

class FakeCollectorRepository: CollectorRepository{
    override suspend fun getCollectors(): List<Collector> {
        return listOf(
            Collector(1,"Manolo Bellon", "3502457896", "manollo@carcol.com.co"),
            Collector(2, "Jaime Monsalve", "3012357936", "jmonsalve@rtv.com.co")
        )
    }

}

class FakeCollectorRepositoryWithError : CollectorRepository{
    override suspend fun getCollectors(): List<Collector> {
        throw Exception("Error de conexion")
    }
}