package com.movilesuniandes.vinilos.features.artists

import com.movilesuniandes.vinilos.features.artists.model.Artist
import com.movilesuniandes.vinilos.features.artists.model.ArtistDto
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import org.junit.Assert.assertEquals
import org.junit.Test

class ArtistRepositoryImplTest {

    private fun ArtistDto.toDomain(kind: ArtistKind) = Artist(
        id = id,
        name = name,
        image = image,
        description = description,
        creationDate = creationDate,
        birthDate = birthDate,
        kind = kind
    )

    @Test
    fun `ArtistDto se mapea correctamente a Artist MUSICO`() {
        val dto = ArtistDto(1, "Rubén Blades", "img", "Cantautor", birthDate = "1948-07-16")
        val artist = dto.toDomain(ArtistKind.MUSICO)

        assertEquals(1, artist.id)
        assertEquals("Rubén Blades", artist.name)
        assertEquals("img", artist.image)
        assertEquals("Cantautor", artist.description)
        assertEquals("1948-07-16", artist.birthDate)
        assertEquals(null, artist.creationDate)
        assertEquals(ArtistKind.MUSICO, artist.kind)
    }

    @Test
    fun `ArtistDto con campos vacios se mapea sin errores`() {
        val dto = ArtistDto(0, "", "", "", creationDate = "", birthDate = "")
        val artist = dto.toDomain(ArtistKind.BANDA)

        assertEquals(0, artist.id)
        assertEquals("", artist.name)
        assertEquals(ArtistKind.BANDA, artist.kind)
    }
}
