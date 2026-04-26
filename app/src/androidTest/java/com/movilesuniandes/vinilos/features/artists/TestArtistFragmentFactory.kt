package com.movilesuniandes.vinilos.features.artists

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository
import com.movilesuniandes.vinilos.features.artists.view.ArtistListFragment

class TestArtistFragmentFactory(
    private val repository: ArtistRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtistListFragment::class.java.name ->
                ArtistListFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
