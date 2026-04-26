package com.movilesuniandes.vinilos.features.artists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistUiState
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistViewModel
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
class ArtistViewModelTest {

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
        val viewModel = ArtistViewModel(FakeArtistRepository())
        assertTrue(viewModel.uiState.value is ArtistUiState.Success)
    }

    @Test
    fun `loadArtists con repositorio exitoso emite Success`() {
        val viewModel = ArtistViewModel(FakeArtistRepository())
        assertTrue(viewModel.uiState.value is ArtistUiState.Success)
    }

    @Test
    fun `loadArtists con error emite Error`() {
        val viewModel = ArtistViewModel(FakeArtistRepositoryWithError())
        assertTrue(viewModel.uiState.value is ArtistUiState.Error)
    }

    @Test
    fun `Success contiene los artistas del repositorio`() {
        val viewModel = ArtistViewModel(FakeArtistRepository())
        val state = viewModel.uiState.value as ArtistUiState.Success
        assertEquals(2, state.artists.size)
        assertEquals("Rubén Blades", state.artists[0].name)
    }

    @Test
    fun `Error contiene el mensaje de la excepcion`() {
        val viewModel = ArtistViewModel(FakeArtistRepositoryWithError())
        val state = viewModel.uiState.value as ArtistUiState.Error
        assertEquals("Error de conexión", state.message)
    }
}
