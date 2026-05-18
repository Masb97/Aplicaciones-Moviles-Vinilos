package com.movilesuniandes.vinilos.features.collector.viewmodel

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import kotlinx.coroutines.launch

sealed class CollectorUiState{
    object Loading: CollectorUiState()
    data class Success(val collectors: List<Collector>): CollectorUiState()
    data class Error(val message: String): CollectorUiState()
}

class CollectorViewModel(
    private val repository: CollectorRepository
): ViewModel(){
    private val _uiState = MutableLiveData<CollectorUiState>(CollectorUiState.Loading)
    val uiState: LiveData<CollectorUiState> = _uiState

    init {
        loadCollectors()
    }

    fun loadCollectors(){
        viewModelScope.launch {
            _uiState.value= CollectorUiState.Loading
            runCatching { repository.getCollectors() }
                .onSuccess { _uiState.value= CollectorUiState.Success(it) }
                .onFailure { _uiState.value= CollectorUiState.Error(it.message?: "Error") }
        }
    }
}