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
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepositoryImpl
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModelFactory
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepository
import com.movilesuniandes.vinilos.features.collector.model.CollectorRepositoryImpl
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorUiState
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorViewModel
import com.movilesuniandes.vinilos.features.collector.viewmodel.CollectorViewModelFactory

class CollectorListFragment(): Fragment() {
    private var repository: CollectorRepository = CollectorRepositoryImpl()
    private val viewModel: CollectorViewModel by viewModels {
        CollectorViewModelFactory(repository)
    }
    private lateinit var adapter: CollectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_collector_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val textError= view.findViewById<TextView>(R.id.textError)

        adapter= CollectorAdapter{ collectorId ->
            // Navigation to detail commented out
            /*
            val bundle= Bundle().apply {
                putInt("collectorId", collectorId)
            }
            androidx.navigation.fragment.NavHostFragment.findNavController(this )
                .navigate(R.id.albumDetailFragment, bundle)
            */
        }

        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter= adapter

        viewModel.uiState.observe(viewLifecycleOwner){ state ->
            when(state){
                is CollectorUiState.Loading ->{
                    progressBar.visibility= View.VISIBLE
                    recyclerView.visibility= View.GONE
                    textError.visibility= View.GONE
                }
                is CollectorUiState.Success ->{
                    progressBar.visibility = View.GONE
                    recyclerView.visibility= View.VISIBLE
                    textError.visibility= View.GONE
                    adapter.submitList(state.collectors)
                }
                is CollectorUiState.Error -> {
                    progressBar.visibility= View.GONE
                    recyclerView.visibility= View.GONE
                    textError.visibility= View.VISIBLE
                    textError.text= state.message
                }
            }

        }
    }

}