package com.movilesuniandes.vinilos.features.collector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorDetail
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorUiState
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorViewModel
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

@OptIn(ExperimentalCoroutinesApi :: class)


class CollectorViewModelTest {
    @get: Rule
    
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher= UnconfinedTestDispatcher()
    
    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }
    
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
    
    @Test
    fun `uiState inicia en loading`(){
        val repository = object : CollectorRepository {
            override suspend fun getCollectors(): List<Collector> {
                kotlinx.coroutines.delay(1000)
                return emptyList()
            }

            override suspend fun getCollectorDetail(id: Int): CollectorDetail {
                error("Not used in this test")
            }
        }

        
        val viewModel = CollectorViewModel(repository)
        
        assertTrue(viewModel.uiState.value is CollectorUiState.Loading)
    }

    @Test
    fun `loadCollectos con repositorio exitoso emite Success`(){
        val viewModel= CollectorViewModel(FakeCollectorRepository())
        assertTrue(viewModel.uiState.value is CollectorUiState.Success)
    }

    @Test
    fun `loadCollector con error emite Error`(){
        val viewModel= CollectorViewModel(FakeCollectorRepositoryWithError())
        assertTrue(viewModel.uiState.value is CollectorUiState.Error)
    }

    @Test
    fun `Sucess contiene los coleccionistas del repositorio`(){
        val viewModel= CollectorViewModel(FakeCollectorRepository())
        val state= viewModel.uiState.value as CollectorUiState.Success
        assertEquals(2, state.collectors.size)
        assertEquals("Manolo Bellon", state.collectors[0].name)
    }

    @Test
    fun `Error contiene el mensaje de la excepcion`(){
        val viewModel= CollectorViewModel(FakeCollectorRepositoryWithError())
        val state = viewModel.uiState.value as CollectorUiState.Error
        assertEquals("Error de conexion", state.message)
    }
}