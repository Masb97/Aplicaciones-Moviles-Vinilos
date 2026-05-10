package com.movilesuniandes.vinilos.features.albums

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.view.AlbumListFragment
import com.movilesuniandes.vinilos.features.albums.view.AlbumDetailFragment

class TestAlbumFragmentFactory(
    private val repository: AlbumRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            AlbumListFragment::class.java.name ->
                AlbumListFragment(repository)
            AlbumDetailFragment::class.java.name ->
                AlbumDetailFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
