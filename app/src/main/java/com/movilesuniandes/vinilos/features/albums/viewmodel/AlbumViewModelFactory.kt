package com.movilesuniandes.vinilos.features.albums.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository

class AlbumViewModelFactory(
    private val repository: AlbumRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AlbumViewModel(repository) as T
    }
}
