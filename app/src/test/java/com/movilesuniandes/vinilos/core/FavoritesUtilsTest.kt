package com.movilesuniandes.vinilos.core

import org.junit.Assert.*
import org.junit.Test

class FavoritesUtilsTest {

    @Test
    fun toggle_adds_id_when_missing() {
        val set = mutableSetOf<Int>()
        FavoritesUtils.toggle(set, 1)
        assertTrue(set.contains(1))
    }

    @Test
    fun toggle_removes_id_when_present() {
        val set = mutableSetOf(2)
        FavoritesUtils.toggle(set, 2)
        assertFalse(set.contains(2))
    }
}
