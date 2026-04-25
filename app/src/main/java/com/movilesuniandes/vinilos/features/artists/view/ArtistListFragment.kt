package com.movilesuniandes.vinilos.features.artists.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.artists.model.Artist
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistUiState
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistViewModel

class ArtistListFragment : Fragment() {

    private enum class FilterType {
        ALL,
        BANDS,
        MUSICIANS,
        FAVORITES
    }

    private val viewModel: ArtistViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter
    private var allArtists: List<Artist> = emptyList()
    private val favoriteArtistIds = mutableSetOf<Int>()
    private var currentFilter: FilterType = FilterType.ALL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_artist_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewArtists)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarArtists)
        val textError = view.findViewById<TextView>(R.id.textErrorArtists)
        val filterGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.filterGroup)

        adapter = ArtistAdapter { artist ->
            toggleFavorite(artist)
            renderFilteredArtists(textError, recyclerView)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                setDrawable(requireContext().getDrawable(R.drawable.divider_artist)!!)
            }
        )

        filterGroup.check(R.id.btnFilterAll)
        filterGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            currentFilter = when (checkedId) {
                R.id.btnFilterBands -> FilterType.BANDS
                R.id.btnFilterMusicians -> FilterType.MUSICIANS
                R.id.btnFilterFavorites -> FilterType.FAVORITES
                else -> FilterType.ALL
            }
            renderFilteredArtists(textError, recyclerView)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ArtistUiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    textError.visibility = View.GONE
                }
                is ArtistUiState.Success -> {
                    progressBar.visibility = View.GONE
                    textError.visibility = View.GONE
                    allArtists = state.artists
                    renderFilteredArtists(textError, recyclerView)
                }
                is ArtistUiState.Error -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.message
                }
            }
        }
    }

    private fun renderFilteredArtists(textError: TextView, recyclerView: RecyclerView) {
        val filtered = when (currentFilter) {
            FilterType.ALL -> allArtists
            FilterType.BANDS -> allArtists.filter { it.kind == ArtistKind.BANDA }
            FilterType.MUSICIANS -> allArtists.filter { it.kind == ArtistKind.MUSICO }
            FilterType.FAVORITES -> allArtists.filter { favoriteArtistIds.contains(it.id) }
        }

        if (filtered.isEmpty()) {
            recyclerView.visibility = View.GONE
            textError.visibility = View.VISIBLE
            textError.text = if (currentFilter == FilterType.FAVORITES) {
                getString(R.string.favorites_empty)
            } else {
                getString(R.string.artists_empty)
            }
            return
        }

        textError.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.updateFavorites(favoriteArtistIds)
        adapter.submitList(filtered)
    }

    private fun toggleFavorite(artist: Artist) {
        if (favoriteArtistIds.contains(artist.id)) {
            favoriteArtistIds.remove(artist.id)
            Toast.makeText(requireContext(), getString(R.string.favorite_removed), Toast.LENGTH_SHORT)
                .show()
        } else {
            favoriteArtistIds.add(artist.id)
            Toast.makeText(requireContext(), getString(R.string.favorite_added), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
