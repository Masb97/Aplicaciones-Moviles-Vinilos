package com.movilesuniandes.vinilos.features.artists.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.artists.model.Artist
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepository
import com.movilesuniandes.vinilos.features.artists.model.ArtistRepositoryImpl
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistDetailUiState
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistDetailViewModel
import com.movilesuniandes.vinilos.features.artists.viewmodel.ArtistDetailViewModelFactory

class ArtistDetailFragment(
    private var repository: ArtistRepository = ArtistRepositoryImpl()
) : Fragment() {

    private val viewModel: ArtistDetailViewModel by viewModels {
        val artistId = arguments?.getInt("artistId") ?: 0
        val artistKindStr = arguments?.getString("artistKind") ?: ArtistKind.MUSICO.name
        val kind = ArtistKind.valueOf(artistKindStr)
        ArtistDetailViewModelFactory(repository, artistId, kind)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_artist_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageArtist = view.findViewById<ImageView>(R.id.imageArtist)
        val textArtistKindBadge = view.findViewById<TextView>(R.id.textArtistKindBadge)
        val textArtistName = view.findViewById<TextView>(R.id.textArtistName)
        val textArtistDescription = view.findViewById<TextView>(R.id.textArtistDescription)
        val labelDate = view.findViewById<TextView>(R.id.labelDate)
        val textDate = view.findViewById<TextView>(R.id.textDate)
        val labelExtra = view.findViewById<TextView>(R.id.labelExtra)
        val textExtra = view.findViewById<TextView>(R.id.textExtra)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarDetail)
        val errorContainer = view.findViewById<LinearLayout>(R.id.errorContainer)
        val btnRetry = view.findViewById<Button>(R.id.btnRetry)

        btnRetry.setOnClickListener {
            viewModel.loadArtistDetail()
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ArtistDetailUiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    errorContainer.visibility = View.GONE
                }
                is ArtistDetailUiState.Success -> {
                    progressBar.visibility = View.GONE
                    errorContainer.visibility = View.GONE
                    bindArtist(
                        state.artist,
                        imageArtist,
                        textArtistKindBadge,
                        textArtistName,
                        textArtistDescription,
                        labelDate,
                        textDate,
                        labelExtra,
                        textExtra
                    )
                }
                is ArtistDetailUiState.Error -> {
                    progressBar.visibility = View.GONE
                    errorContainer.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun bindArtist(
        artist: Artist,
        imageArtist: ImageView,
        textArtistKindBadge: TextView,
        textArtistName: TextView,
        textArtistDescription: TextView,
        labelDate: TextView,
        textDate: TextView,
        labelExtra: TextView,
        textExtra: TextView
    ) {
        imageArtist.load(artist.image) {
            crossfade(true)
            placeholder(R.drawable.bg_album_list)
            error(R.drawable.ic_launcher_background)
        }
        textArtistName.text = artist.name
        textArtistDescription.text = artist.description

        if (artist.kind == ArtistKind.MUSICO) {
            textArtistKindBadge.text = getString(R.string.artist_kind_musician)
            textArtistKindBadge.setBackgroundResource(R.drawable.bg_artist_kind_badge)
            labelDate.text = getString(R.string.artist_label_birth)
            textDate.text = artist.birthDate?.substringBefore("T") ?: getString(R.string.artist_date_unavailable)
            labelExtra.text = getString(R.string.artist_label_associated_band)
            textExtra.text = getString(R.string.artist_not_available)
        } else {
            textArtistKindBadge.text = getString(R.string.artist_kind_band)
            textArtistKindBadge.setBackgroundResource(R.drawable.bg_artist_kind_badge)
            labelDate.text = getString(R.string.artist_label_creation)
            textDate.text = artist.creationDate?.substringBefore("T") ?: getString(R.string.artist_date_unavailable)
            labelExtra.text = getString(R.string.artist_label_members)
            textExtra.text = getString(R.string.artist_view_list)
        }
    }
}
