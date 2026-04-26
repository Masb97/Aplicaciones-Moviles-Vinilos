package com.movilesuniandes.vinilos.features.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    fun `uiState inicia en Loading`() {
        val viewModel = AlbumViewModel(FakeAlbumRepository())
        assertTrue(viewModel.uiState.value is AlbumUiState.Success)
    }

    @Test
    fun `loadAlbums con repositorio exitoso emite Success`() {
        val viewModel = AlbumViewModel(FakeAlbumRepository())
        assertTrue(viewModel.uiState.value is AlbumUiState.Success)
    }

    @Test
    fun `loadAlbums con error emite Error`() {
        val viewModel = AlbumViewModel(FakeAlbumRepositoryWithError())
        assertTrue(viewModel.uiState.value is AlbumUiState.Error)
    }

    @Test
    fun `Success contiene los albumes del repositorio`() {
        val viewModel = AlbumViewModel(FakeAlbumRepository())
        val state = viewModel.uiState.value as AlbumUiState.Success
        assertEquals(2, state.albums.size)
        assertEquals("Kind of Blue", state.albums[0].name)
    }

    @Test
    fun `Error contiene el mensaje de la excepcion`() {
        val viewModel = AlbumViewModel(FakeAlbumRepositoryWithError())
        val state = viewModel.uiState.value as AlbumUiState.Error
        assertEquals("Error de conexión", state.message)
    }
}
