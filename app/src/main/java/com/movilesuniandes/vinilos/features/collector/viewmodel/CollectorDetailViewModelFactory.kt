package com.movilesuniandes.vinilos.features.collector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository

class CollectorDetailViewModelFactory(
    private val repository: CollectorRepository,
    private val collectorId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CollectorDetailViewModel(repository, collectorId) as T
    }
}