package com.movilesuniandes.vinilos.features.artists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistViewModel
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistViewModelFactory
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
class ArtistViewModelFactoryTest {

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
    fun `create retorna una instancia de ArtistViewModel`() {
        val factory = ArtistViewModelFactory(FakeArtistRepository())
        val viewModel = factory.create(ArtistViewModel::class.java)
        assertTrue(viewModel is ArtistViewModel)
    }
}
