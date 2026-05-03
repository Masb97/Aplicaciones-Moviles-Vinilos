package com.movilesuniandes.vinilos.features.collector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import kotlin.contracts.Returns

class CollectorViewModelFactory(
    private val repository: CollectorRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CollectorViewModel(repository) as T
    }
}