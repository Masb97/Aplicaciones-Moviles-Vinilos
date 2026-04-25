package com.movilesuniandes.vinilos.features.artists.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository

class ArtistViewModelFactory(
    private val repository: ArtistRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ArtistViewModel(repository) as T
    }
}
