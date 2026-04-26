package com.movilesuniandes.vinilos.features.albums

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.view.AlbumListFragment

class TestAlbumFragmentFactory(
    private val repository: AlbumRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            AlbumListFragment::class.java.name ->
                AlbumListFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
