package com.movilesuniandes.vinilos.features.collector

import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorDto
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class CollectorRepositoryImplTest {
    private fun CollectorDto.toDomain()= Collector(
        id = id,
        name= name,
        telephone= telephone,
        email= email
    )

    @Test
    fun `CollectorDto se mapea correctamente a Collector`(){
        val dto = CollectorDto(1, "Manolo Bellon", "3502457896", "manollo@carocol.com.co")
        val collector= dto.toDomain()

        assertEquals(1, collector.id)
        assertEquals("Manolo Bellon", collector.name)
        assertEquals("3502457896", collector.telephone)
        assertEquals("manollo@carocol.com.co", collector.email)
    }

    @Test
    fun `CollectorDto con campos vacios se mapea sin errores`(){
        val dto= CollectorDto(0, "","", "")
        val collector= dto.toDomain()
        assertEquals(0, collector.id)
        assertEquals("", collector.name)
    }

    @Test
    fun `mapeo de lista de CollectorDto a lista de Collector mantiene orden y contenido`(){
        val dtos = listOf(
            CollectorDto(1, "Coleccionista 1", "123", "c1@test.com"),
            CollectorDto(2, "Coleccionista 2", "456", "c2@test.com")
        )

        val collectors = dtos.map { it.toDomain() }

        assertEquals(2, collectors.size)
        assertEquals(dtos[0].id, collectors[0].id)
        assertEquals(dtos[1].name, collectors[1].name)
        assertEquals(dtos[1].email, collectors[1].email)
    }

    @Test
    fun `mapeo maneja correctamente nombres con caracteres especiales`(){
        val nombreConEspeciales = "Ramón Valdés y Cía. Ø"
        val dto = CollectorDto(1, nombreConEspeciales, "123", "test@test.com")

        val collector = dto.toDomain()

        assertEquals(nombreConEspeciales, collector.name)
    }

    @Test
    fun `mapeo de lista vacia retorna lista vacia`(){
        val dtos = emptyList<CollectorDto>()
        val collectors = dtos.map { it.toDomain() }
        assertTrue(collectors.isEmpty())
    }
}