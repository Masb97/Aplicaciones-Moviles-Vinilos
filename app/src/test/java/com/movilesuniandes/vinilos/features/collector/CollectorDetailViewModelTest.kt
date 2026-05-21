package com.movilesuniandes.vinilos.features.collector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailUiState
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailViewModel
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
class CollectorDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() { Dispatchers.setMain(testDispatcher) }

    @After
    fun tearDown() { Dispatchers.resetMain() }

    @Test
    fun `loadCollectorDetail con repositorio exitoso emite Success`() {
        val viewModel = CollectorDetailViewModel(FakeCollectorRepository(), collectorId = 1)
        assertTrue(viewModel.uiState.value is CollectorDetailUiState.Success)
    }

    @Test
    fun `loadCollectorDetail con error emite Error`() {
        val viewModel = CollectorDetailViewModel(FakeCollectorRepositoryWithError(), collectorId = 1)
        assertTrue(viewModel.uiState.value is CollectorDetailUiState.Error)
    }

    @Test
    fun `Success contiene el coleccionista correcto`() {
        val viewModel = CollectorDetailViewModel(FakeCollectorRepository(), collectorId = 1)
        val state = viewModel.uiState.value as CollectorDetailUiState.Success
        assertEquals("Manolo Bellon", state.collector.name)
        assertEquals("manollo@caracol.com.co", state.collector.email)
    }

    @Test
    fun `Success contiene los artistas favoritos del coleccionista`() {
        val viewModel = CollectorDetailViewModel(FakeCollectorRepository(), collectorId = 1)
        val state = viewModel.uiState.value as CollectorDetailUiState.Success
        assertEquals(1, state.collector.favoritePerformers.size)
        assertEquals("Rubén Blades", state.collector.favoritePerformers[0].name)
    }

    @Test
    fun `Error contiene el mensaje de la excepcion`() {
        val viewModel = CollectorDetailViewModel(FakeCollectorRepositoryWithError(), collectorId = 1)
        val state = viewModel.uiState.value as CollectorDetailUiState.Error
        assertEquals("Error de conexion", state.message)
    }
}
