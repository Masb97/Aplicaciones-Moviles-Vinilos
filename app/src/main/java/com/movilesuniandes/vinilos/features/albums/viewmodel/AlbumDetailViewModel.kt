package com.movilesuniandes.vinilos.features.albums.viewmodel

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import kotlinx.coroutines.launch

sealed class AlbumDetailUiState{
    object Loading: AlbumDetailUiState()
    data class Success(val album: Album): AlbumDetailUiState()
    data class Error(val message: String) : AlbumDetailUiState()
}

class AlbumDetailViewModel (private val repository: AlbumRepository): ViewModel(){
    private val _uiState= MutableLiveData<AlbumDetailUiState>()
    val uiState: LiveData<AlbumDetailUiState> = _uiState

    fun loadAlbum(id: Int){
        viewModelScope.launch {
            _uiState.value= AlbumDetailUiState.Loading
            runCatching { repository.getAlbumById(id) }
                .onSuccess { _uiState.value= AlbumDetailUiState.Success(it) }
                .onFailure { _uiState.value= AlbumDetailUiState.Error(it.message?: "Error") }

        }
    }
}