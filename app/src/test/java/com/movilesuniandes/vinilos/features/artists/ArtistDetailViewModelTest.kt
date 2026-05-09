package com.movilesuniandes.vinilos.features.artists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistDetailUiState
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistDetailViewModel
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
class ArtistDetailViewModelTest {

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
    fun `loadArtistDetail con repositorio exitoso emite Success`() {
        val viewModel = ArtistDetailViewModel(FakeArtistRepository(), 1, ArtistKind.MUSICO)
        assertTrue(viewModel.uiState.value is ArtistDetailUiState.Success)
    }

    @Test
    fun `loadArtistDetail con error emite Error`() {
        val viewModel = ArtistDetailViewModel(FakeArtistRepositoryWithError(), 1, ArtistKind.MUSICO)
        assertTrue(viewModel.uiState.value is ArtistDetailUiState.Error)
    }

    @Test
    fun `Success contiene el artista correcto`() {
        val viewModel = ArtistDetailViewModel(FakeArtistRepository(), 1, ArtistKind.MUSICO)
        val state = viewModel.uiState.value as ArtistDetailUiState.Success
        assertEquals(1, state.artist.id)
        assertEquals("Rubén Blades", state.artist.name)
        assertEquals(ArtistKind.MUSICO, state.artist.kind)
    }

    @Test
    fun `Error contiene el mensaje de la excepcion`() {
        val viewModel = ArtistDetailViewModel(FakeArtistRepositoryWithError(), 1, ArtistKind.MUSICO)
        val state = viewModel.uiState.value as ArtistDetailUiState.Error
        assertEquals("Error de conexión", state.message)
    }
}
