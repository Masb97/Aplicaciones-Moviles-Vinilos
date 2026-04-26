package com.movilesuniandes.vinilos.features.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModel
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumViewModelFactoryTest {

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
    fun `create retorna una instancia de AlbumViewModel`() {
        val factory = AlbumViewModelFactory(FakeAlbumRepository())
        val viewModel = factory.create(AlbumViewModel::class.java)
        assertTrue(viewModel is AlbumViewModel)
    }
}
