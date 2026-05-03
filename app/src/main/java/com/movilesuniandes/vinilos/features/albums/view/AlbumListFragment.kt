package com.movilesuniandes.vinilos.features.albums.view

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
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModel
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModelFactory

class AlbumListFragment() : Fragment() {
    private var repository: AlbumRepository = AlbumRepositoryImpl()
    private val viewModel: AlbumViewModel by viewModels {
        AlbumViewModelFactory(repository)
    }
    private lateinit var adapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_album_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val textError = view.findViewById<TextView>(R.id.textError)

        adapter = AlbumAdapter{ albumId ->
            val bundle = Bundle().apply {
                putInt("albumId", albumId)
            }
            androidx.navigation.fragment.NavHostFragment.findNavController(this)
                .navigate(R.id.albumDetailFragment, bundle)

        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AlbumUiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    textError.visibility = View.GONE
                }
                is AlbumUiState.Success -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    textError.visibility = View.GONE
                    adapter.submitList(state.albums)
                }
                is AlbumUiState.Error -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.message
                }
            }
        }
    }
}
