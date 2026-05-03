package com.movilesuniandes.vinilos.features.albums.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository

class AlbumDetailViewModelFactory(private val repository: AlbumRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)){
            return AlbumDetailViewModel(repository) as  T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}