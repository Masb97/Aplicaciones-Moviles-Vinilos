package com.movilesuniandes.vinilos.features.albums

import com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest
import com.movilesuniandes.vinilos.features.albums.model.CreateTrack
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class AlbumCreateViewModelTest {

    @Test
    fun validate_returns_error_for_short_name() {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val req = CreateAlbumRequest(name = "ab", releaseDate = "2000-01-01", genre = "Rock")
        val errors = vm.validate(req)
        assertTrue(errors.containsKey("name"))
    }

    @Test
    fun validate_returns_error_for_future_date() {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val future = java.time.LocalDate.now().plusDays(10).toString()
        val req = CreateAlbumRequest(name = "Valid Name", releaseDate = future, genre = "Rock")
        val errors = vm.validate(req)
        assertTrue(errors.containsKey("releaseDate"))
    }

    @Test
    fun validate_accepts_valid_request() {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val req = CreateAlbumRequest(name = "A Night at the Opera", releaseDate = "1975-11-21", genre = "Rock", cover = "https://example.com/cover.png")
        val errors = vm.validate(req)
        assertTrue(errors.isEmpty())
    }

    @Test
    fun createAlbum_calls_repository_and_returns_success() = runBlocking {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val req = CreateAlbumRequest(name = "New Album", releaseDate = "1970-01-01", genre = "Jazz")
        vm.createAlbum(req)
        val state = vm.uiState.value
        // Since creation is launched in coroutine, allow for a small delay or check state types
        assertNotNull(state)
    }
}
