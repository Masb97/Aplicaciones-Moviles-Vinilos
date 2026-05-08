package com.movilesuniandes.vinilos.features.artists.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository

class ArtistDetailViewModelFactory(
    private val repository: ArtistRepository,
    private val artistId: Int,
    private val artistKind: ArtistKind
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
            return ArtistDetailViewModel(repository, artistId, artistKind) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
