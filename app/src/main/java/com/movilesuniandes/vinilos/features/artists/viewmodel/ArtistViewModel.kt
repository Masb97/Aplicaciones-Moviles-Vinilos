package com.movilesuniandes.vinilos.features.artists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.artists.model.Artist
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository
import kotlinx.coroutines.launch

sealed class ArtistUiState {
    object Loading : ArtistUiState()
    data class Success(val artists: List<Artist>) : ArtistUiState()
    data class Error(val message: String) : ArtistUiState()
}

class ArtistViewModel(
    private val repository: ArtistRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<ArtistUiState>(ArtistUiState.Loading)
    val uiState: LiveData<ArtistUiState> = _uiState

    init {
        loadArtists()
    }

    fun loadArtists() {
        viewModelScope.launch {
            _uiState.value = ArtistUiState.Loading
            runCatching { repository.getArtists() }
                .onSuccess { _uiState.value = ArtistUiState.Success(it) }
                .onFailure { _uiState.value = ArtistUiState.Error(it.message ?: "Error") }
        }
    }
}
