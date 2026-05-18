package com.movilesuniandes.vinilos.features.collector.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.collector.model.Collector
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectorDetailFragment(
    private var repository: CollectorRepository = CollectorRepositoryImpl()
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_collector_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val collectorId = arguments?.getInt("collectorId") ?: 0
        val nameView = view.findViewById<TextView>(R.id.textCollectorName)
        val emailView = view.findViewById<TextView>(R.id.textCollectorEmail)

        // load collectors and find by id
        CoroutineScope(Dispatchers.Main).launch {
            val collectors = repository.getCollectors()
            val c = collectors.firstOrNull { it.id == collectorId }
            if (c != null) bindCollector(c, nameView, emailView)
        }
    }

    private fun bindCollector(c: Collector, nameView: TextView, emailView: TextView) {
        nameView.text = c.name
        emailView.text = c.email
    }
}
