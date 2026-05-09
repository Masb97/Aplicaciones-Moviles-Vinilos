package com.movilesuniandes.vinilos.features.artists.viewmodel

import com.movilesuniandes.vinilos.features.artists.model.Artist

sealed class ArtistDetailUiState {
    object Loading : ArtistDetailUiState()
    data class Success(val artist: Artist) : ArtistDetailUiState()
    data class Error(val message: String) : ArtistDetailUiState()
}
