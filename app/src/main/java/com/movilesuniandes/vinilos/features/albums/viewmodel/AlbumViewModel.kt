package com.movilesuniandes.vinilos.features.albums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepositoryImpl
import kotlinx.coroutines.launch

sealed class AlbumUiState {
    object Loading : AlbumUiState()
    data class Success(val albums: List<Album>) : AlbumUiState()
    data class Error(val message: String) : AlbumUiState()
}

class AlbumViewModel : ViewModel() {

    private val repository: AlbumRepository = AlbumRepositoryImpl()

    private val _uiState = MutableLiveData<AlbumUiState>(AlbumUiState.Loading)
    val uiState: LiveData<AlbumUiState> = _uiState

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        viewModelScope.launch {
            _uiState.value = AlbumUiState.Loading
            runCatching { repository.getAlbums() }
                .onSuccess { _uiState.value = AlbumUiState.Success(it) }
                .onFailure { _uiState.value = AlbumUiState.Error(it.message ?: "Error") }
        }
    }
}
