package com.movilesuniandes.vinilos.features.collector

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.view.CollectorListFragment

class TestCollectorFragmentFactory(
    private val repository: CollectorRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CollectorListFragment::class.java.name ->
                CollectorListFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
