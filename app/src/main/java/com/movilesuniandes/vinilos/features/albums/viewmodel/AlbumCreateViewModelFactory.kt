package com.movilesuniandes.vinilos.features.albums.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository

class AlbumCreateViewModelFactory(
    private val repository: AlbumRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlbumCreateViewModel(repository) as T
    }
}
