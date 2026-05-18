package com.movilesuniandes.vinilos.features.albums

import com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumCreateViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

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
        val future = java.time.LocalDate.now().plusDays(10)
            .atStartOfDay(java.time.ZoneId.systemDefault())
            .toInstant()
            .toString()
        val req = CreateAlbumRequest(name = "Valid Name", releaseDate = future, genre = "Rock", recordLabel = "EMI")
        val errors = vm.validate(req)
        assertTrue(errors.containsKey("releaseDate"))
    }

    @Test
    fun validate_accepts_valid_request() {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val req = CreateAlbumRequest(
            name = "A Night at the Opera",
            releaseDate = "1975-11-21T00:00:00Z",
            genre = "Rock",
            cover = "https://example.com/cover.png",
            recordLabel = "EMI"
        )
        val errors = vm.validate(req)
        assertTrue(errors.isEmpty())
    }

    @Test
    fun validate_returns_error_for_missing_record_label() {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val req = CreateAlbumRequest(
            name = "Valid Name",
            releaseDate = "1975-11-21T00:00:00Z",
            genre = "Rock",
            cover = "https://example.com/cover.png"
        )
        val errors = vm.validate(req)
        assertTrue(errors.containsKey("recordLabel"))
    }

    @Test
    fun createAlbum_with_invalid_request_emits_validation_error() {
        val vm = AlbumCreateViewModel(FakeAlbumRepository())
        val req = CreateAlbumRequest(
            name = "ab",
            releaseDate = "invalid-date",
            genre = "",
            recordLabel = null
        )

        val errors = vm.validate(req)

        assertTrue(errors.containsKey("name"))
        assertTrue(errors.containsKey("releaseDate"))
        assertTrue(errors.containsKey("genre"))
        assertTrue(errors.containsKey("recordLabel"))
    }
}
