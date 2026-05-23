package com.movilesuniandes.vinilos.features.collector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailViewModel
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CollectorDetailViewModelFactoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() { Dispatchers.setMain(testDispatcher) }

    @After
    fun tearDown() { Dispatchers.resetMain() }

    @Test
    fun `create retorna una instancia de CollectorDetailViewModel`() {
        val factory = CollectorDetailViewModelFactory(FakeCollectorRepository(), collectorId = 1)
        val viewModel = factory.create(CollectorDetailViewModel::class.java)
        assertNotNull(viewModel)
    }
}
