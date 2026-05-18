package com.movilesuniandes.vinilos.features.albums

import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumDetailUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Instant

@OptIn(ExperimentalCoroutinesApi :: class)
class AlbumDetailViewModelTest {
    @get: Rule
    val instantTaskExecutorRule= InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `loadAlbumDetail con repositorio existoso emite Sucess`(){
        val repository= FakeAlbumRepository()
        val viewModel= AlbumDetailViewModel(repository)
        val albumId= 1
        viewModel.loadAlbum(albumId)
        assertTrue(viewModel.uiState.value is AlbumDetailUiState.Success)
    }

    @Test
    fun `loadAlbumDetail con error emite Error`(){
        val repository= FakeAlbumRepositoryWithError()
        val viewModel= AlbumDetailViewModel(repository)
        val albumId= 1
        viewModel.loadAlbum(albumId)
        assertTrue(viewModel.uiState.value is AlbumDetailUiState.Error)
    }

    @Test
    fun `Sucess contiene el album correcto`(){
        val  repository = FakeAlbumRepository()
        val viewModel= AlbumDetailViewModel(repository)
        val albumId =1
        viewModel.loadAlbum(albumId)
        val state= viewModel.uiState.value as AlbumDetailUiState.Success
        assertEquals(1,state.album.id)
        assertEquals("Kind of Blue", state.album.name)
        assertEquals("", state.album.cover)
        assertEquals("1959-08-17", state.album.releaseDate)
        assertEquals("Jazz modal", state.album.description)
        assertEquals("Jazz", state.album.genre)
        assertEquals("Columbia", state.album.recordLabel)
    }

    @Test
    fun `Error contiene el mensaje de la excepcion` (){
        val  repository = FakeAlbumRepositoryWithError()
        val viewModel= AlbumDetailViewModel(repository)
        val albumId =1
        viewModel.loadAlbum(albumId)
        val state= viewModel.uiState.value as AlbumDetailUiState.Error
        assertEquals("Error de conexión", state.message)
    }

    @Test
    fun `loadAlbum con excepcion sin mensaje retorna mensaje por defecto`() {
        val repository = object : AlbumRepository {
            override suspend fun getAlbums(): List<Album> = emptyList()
            override suspend fun getAlbumById(id: Int): Album = throw Exception()
            override suspend fun createAlbum(request: com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest): Album {
                throw Exception()
            }
        }
        val viewModel = AlbumDetailViewModel(repository)
        viewModel.loadAlbum(1)
        val state = viewModel.uiState.value as AlbumDetailUiState.Error
        assertEquals("Error", state.message)
    }

    @Test
    fun `al cargar multiples albumes el estado se actualiza correctamente`() {
        val repository = FakeAlbumRepository()
        val viewModel = AlbumDetailViewModel(repository)

        // Carga 1
        viewModel.loadAlbum(1)
        assertTrue(viewModel.uiState.value is AlbumDetailUiState.Success)
        assertEquals(1, (viewModel.uiState.value as AlbumDetailUiState.Success).album.id)

        // Carga 2
        viewModel.loadAlbum(2)
        assertTrue(viewModel.uiState.value is AlbumDetailUiState.Success)
        assertEquals(2, (viewModel.uiState.value as AlbumDetailUiState.Success).album.id)
    }

    @Test
    fun `verificar que el id solicitado al repositorio es el correcto`() {
        var idCaptured = -1
        val repository = object : AlbumRepository {
            override suspend fun getAlbums(): List<Album> = emptyList()
            override suspend fun getAlbumById(id: Int): Album {
                idCaptured = id
                return Album(id, "Test", "", "", "", "", "")
            }
            override suspend fun createAlbum(request: com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest): Album {
                throw Exception()
            }
        }
        val viewModel = AlbumDetailViewModel(repository)
        val idExpected = 99

        viewModel.loadAlbum(idExpected)

        assertEquals(idExpected, idCaptured )
    }

}