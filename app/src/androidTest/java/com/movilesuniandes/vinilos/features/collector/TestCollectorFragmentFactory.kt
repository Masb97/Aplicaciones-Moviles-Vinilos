package com.movilesuniandes.vinilos.features.collector

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.view.CollectorDetailFragment
import com.movilesuniandes.vinilos.features.collector.view.CollectorListFragment

class TestCollectorFragmentFactory(
    private val repository: CollectorRepository,
    private val collectorId: Int = 1
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CollectorListFragment::class.java.name ->
                CollectorListFragment(repository)
            CollectorDetailFragment::class.java.name ->
                CollectorDetailFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
