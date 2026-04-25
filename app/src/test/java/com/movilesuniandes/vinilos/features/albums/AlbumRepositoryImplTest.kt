package com.movilesuniandes.vinilos.features.albums

import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumDto
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumRepositoryImplTest {

    private fun AlbumDto.toDomain() = Album(
        id = id,
        name = name,
        cover = cover,
        releaseDate = releaseDate,
        description = description,
        genre = genre,
        recordLabel = recordLabel
    )

    @Test
    fun `AlbumDto se mapea correctamente a Album`() {
        val dto = AlbumDto(1, "Kind of Blue", "url", "1959-08-17", "Jazz modal", "Jazz", "Columbia")
        val album = dto.toDomain()

        assertEquals(1, album.id)
        assertEquals("Kind of Blue", album.name)
        assertEquals("url", album.cover)
        assertEquals("1959-08-17", album.releaseDate)
        assertEquals("Jazz modal", album.description)
        assertEquals("Jazz", album.genre)
        assertEquals("Columbia", album.recordLabel)
    }

    @Test
    fun `AlbumDto con campos vacios se mapea sin errores`() {
        val dto = AlbumDto(0, "", "", "", "", "", "")
        val album = dto.toDomain()

        assertEquals(0, album.id)
        assertEquals("", album.name)
    }
}
