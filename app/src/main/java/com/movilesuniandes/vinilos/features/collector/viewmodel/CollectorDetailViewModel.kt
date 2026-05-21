package com.movilesuniandes.vinilos.features.collector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.collector.model.CollectorDetail
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import kotlinx.coroutines.launch

sealed class CollectorDetailUiState {
    object Loading : CollectorDetailUiState()
    data class Success(val collector: CollectorDetail) : CollectorDetailUiState()
    data class Error(val message: String) : CollectorDetailUiState()
}

class CollectorDetailViewModel(
    private val repository: CollectorRepository,
    private val collectorId: Int
) : ViewModel() {

    private val _uiState = MutableLiveData<CollectorDetailUiState>(CollectorDetailUiState.Loading)
    val uiState: LiveData<CollectorDetailUiState> = _uiState

    init {
        loadCollectorDetail()
    }

    fun loadCollectorDetail() {
        viewModelScope.launch {
            _uiState.value = CollectorDetailUiState.Loading
            runCatching { repository.getCollectorDetail(collectorId) }
                .onSuccess { _uiState.value = CollectorDetailUiState.Success(it) }
                .onFailure { _uiState.value = CollectorDetailUiState.Error(it.message ?: "Error") }
        }
    }
}