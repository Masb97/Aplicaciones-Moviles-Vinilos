package com.movilesuniandes.vinilos.features.artists.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object FavoritesStore {
    private val _favorites = MutableLiveData<Set<Int>>(emptySet())
    val favorites: LiveData<Set<Int>> = _favorites

    fun toggle(artistId: Int) {
        val current = _favorites.value ?: emptySet()
        _favorites.value = if (current.contains(artistId)) current - artistId else current + artistId
    }

    fun contains(artistId: Int): Boolean = _favorites.value?.contains(artistId) ?: false

    // Test helpers
    fun setFavoritesForTest(favs: Set<Int>) {
        _favorites.value = favs
    }

    fun clearFavoritesForTest() {
        _favorites.value = emptySet()
    }
}
