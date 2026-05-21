package com.movilesuniandes.vinilos.features.collector.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.collector.model.CollectorDetail
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepositoryImpl
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailUiState
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailViewModel
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorDetailViewModelFactory

class CollectorDetailFragment(
    private val repository: CollectorRepository = CollectorRepositoryImpl()
) : Fragment() {

    private val collectorId: Int by lazy {
        arguments?.getInt(ARG_COLLECTOR_ID)
            ?: error("CollectorDetailFragment requires ARG_COLLECTOR_ID")
    }

    private val viewModel: CollectorDetailViewModel by viewModels {
        CollectorDetailViewModelFactory(repository, collectorId)
    }

    private lateinit var adapter: FavoritePerformerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_collector_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val textError = view.findViewById<TextView>(R.id.textError)
        val scrollContent = view.findViewById<View>(R.id.scrollContent)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerPerformers)
        val textNoPerformers = view.findViewById<TextView>(R.id.textNoPerformers)

        adapter = FavoritePerformerAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        recycler.setHasFixedSize(false)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CollectorDetailUiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    scrollContent.visibility = View.GONE
                    textError.visibility = View.GONE
                }
                is CollectorDetailUiState.Success -> {
                    progressBar.visibility = View.GONE
                    textError.visibility = View.GONE
                    scrollContent.visibility = View.VISIBLE
                    bindCollector(view, state.collector, textNoPerformers)
                }
                is CollectorDetailUiState.Error -> {
                    progressBar.visibility = View.GONE
                    scrollContent.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.message
                }
            }
        }
    }

    private fun bindCollector(
        view: View,
        collector: CollectorDetail,
        textNoPerformers: TextView
    ) {
        view.findViewById<ShapeableImageView>(R.id.imageCollector).apply {
            load(R.drawable.ic_collectors) { crossfade(true) }
            contentDescription = getString(R.string.collector_image_description, collector.name)
        }

        view.findViewById<TextView>(R.id.textCollectorName).text = collector.name

        view.findViewById<TextView>(R.id.textCollectorEmail).apply {
            text = collector.email
            contentDescription = getString(R.string.collector_email_description, collector.email)
        }

        view.findViewById<TextView>(R.id.textCollectorPhone).apply {
            text = collector.telephone
            contentDescription = getString(R.string.collector_phone_description, collector.telephone)
        }

        view.findViewById<TextView>(R.id.textStatAlbums).text =
            collector.collectorAlbums.size.toString()

        view.findViewById<TextView>(R.id.textStatFavorites).text =
            collector.favoritePerformers.size.toString()

        view.findViewById<TextView>(R.id.textStatValue).text = getString(
            R.string.collector_value_format,
            collector.collectorAlbums.sumOf { it.price }
        )

        val avgRating = if (collector.comments.isEmpty()) 0.0
        else collector.comments.map { it.rating }.average()
        view.findViewById<TextView>(R.id.textStatRating).text =
            getString(R.string.collector_rating_format, avgRating)

        if (collector.favoritePerformers.isEmpty()) {
            textNoPerformers.visibility = View.VISIBLE
            view.findViewById<RecyclerView>(R.id.recyclerPerformers).visibility = View.GONE
        } else {
            textNoPerformers.visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerPerformers).visibility = View.VISIBLE
            adapter.submitList(collector.favoritePerformers)
        }
    }

    companion object {
        const val ARG_COLLECTOR_ID = "collector_id"

        fun newInstance(collectorId: Int) = CollectorDetailFragment().apply {
            arguments = Bundle().apply { putInt(ARG_COLLECTOR_ID, collectorId) }
        }
    }
}
