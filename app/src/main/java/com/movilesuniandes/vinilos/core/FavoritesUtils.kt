package com.movilesuniandes.vinilos.core

object FavoritesUtils {
    fun toggle(favs: MutableSet<Int>, id: Int): MutableSet<Int> {
        if (favs.contains(id)) favs.remove(id) else favs.add(id)
        return favs
    }
}
