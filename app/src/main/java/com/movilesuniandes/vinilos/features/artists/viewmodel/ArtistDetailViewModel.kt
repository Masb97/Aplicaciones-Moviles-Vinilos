package com.movilesuniandes.vinilos.features.artists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository
import kotlinx.coroutines.launch

class ArtistDetailViewModel(
    private val repository: ArtistRepository,
    private val artistId: Int,
    private val artistKind: ArtistKind
) : ViewModel() {

    private val _uiState = MutableLiveData<ArtistDetailUiState>(ArtistDetailUiState.Loading)
    val uiState: LiveData<ArtistDetailUiState> = _uiState

    init {
        loadArtistDetail()
    }

    fun loadArtistDetail() {
        viewModelScope.launch {
            _uiState.value = ArtistDetailUiState.Loading
            runCatching { repository.getArtistDetail(artistId, artistKind) }
                .onSuccess { _uiState.value = ArtistDetailUiState.Success(it) }
                .onFailure { _uiState.value = ArtistDetailUiState.Error(it.message ?: "Error al cargar el detalle") }
        }
    }
}
