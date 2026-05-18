package com.movilesuniandes.vinilos.features.albums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest
import com.movilesuniandes.vinilos.features.albums.model.CreateTrack
import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

sealed class AlbumCreateUiState {
    object Idle: AlbumCreateUiState()
    object Loading: AlbumCreateUiState()
    data class Success(val album: Album): AlbumCreateUiState()
    data class Error(val message: String): AlbumCreateUiState()
    data class ValidationError(val fieldErrors: Map<String, String>): AlbumCreateUiState()
}

class AlbumCreateViewModel(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<AlbumCreateUiState>(AlbumCreateUiState.Idle)
    val uiState: LiveData<AlbumCreateUiState> = _uiState

    fun validate(request: CreateAlbumRequest): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (request.name.isBlank() || request.name.length < 3 || request.name.length > 200) {
            errors["name"] = "El nombre debe tener entre 3 y 200 caracteres"
        }
        try {
            val parsedDate = parseReleaseDate(request.releaseDate)
            if (parsedDate.isAfter(LocalDate.now())) {
                errors["releaseDate"] = "La fecha no puede ser futura"
            }
        } catch (e: Exception) {
            errors["releaseDate"] = "Fecha inválida; use formato ISO"
        }
        if (request.genre.isBlank()) {
            errors["genre"] = "El género es obligatorio"
        }
        if (request.recordLabel.isNullOrBlank()) {
            errors["recordLabel"] = "El sello discográfico es obligatorio"
        }
        request.cover?.let {
            if (!isValidUrl(it)) {
                errors["cover"] = "URL de portada inválida"
            }
        }
        request.description?.let {
            if (it.length > 2000) errors["description"] = "Descripción demasiado larga"
        }
        request.tracks?.forEachIndexed { idx, t ->
            if (t.name.isBlank()) errors["tracks[$idx].name"] = "El nombre de la pista es obligatorio"
            if (!isValidDuration(t.duration)) errors["tracks[$idx].duration"] = "Duración inválida"
        }

        return errors
    }

    private fun parseReleaseDate(value: String): LocalDate {
        return try {
            OffsetDateTime.parse(value).toLocalDate()
        } catch (_: Exception) {
            LocalDate.parse(value, DateTimeFormatter.ISO_DATE)
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            val u = java.net.URL(url)
            u.protocol == "http" || u.protocol == "https"
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidDuration(d: String): Boolean {
        val mmss = Regex("^\\d{1,2}:[0-5][0-9]")
        val hhmmss = Regex("^\\d{1,2}:[0-5][0-9]:[0-5][0-9]")
        return mmss.matches(d) || hhmmss.matches(d)
    }

    fun createAlbum(request: CreateAlbumRequest) {
        val errors = validate(request)
        if (errors.isNotEmpty()) {
            _uiState.value = AlbumCreateUiState.ValidationError(errors)
            return
        }
        viewModelScope.launch {
            _uiState.value = AlbumCreateUiState.Loading
            runCatching { repository.createAlbum(request) }
                .onSuccess { _uiState.value = AlbumCreateUiState.Success(it) }
                .onFailure { _uiState.value = AlbumCreateUiState.Error(it.message ?: "Error") }
        }
    }
}
