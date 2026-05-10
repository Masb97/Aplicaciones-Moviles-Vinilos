package com.movilesuniandes.vinilos.features.collector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.artists.FakeArtistRepository
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorViewModel
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class CollectorViewModelFactoryTest {
    @get: Rule
    val instantTaskExecutorRule= InstantTaskExecutorRule()

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
    fun `create retorna una instancia de CollectorViewModel`(){
        val factory = CollectorViewModelFactory(FakeCollectorRepository())
        val viewModel= factory.create(CollectorViewModel::class.java)
        assertNotNull(viewModel)
    }
}