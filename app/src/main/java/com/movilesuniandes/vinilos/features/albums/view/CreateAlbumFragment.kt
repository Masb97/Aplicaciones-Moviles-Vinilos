package com.movilesuniandes.vinilos.features.albums.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepositoryImpl
import com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest
import com.movilesuniandes.vinilos.features.albums.model.CreateTrack
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateViewModel
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateViewModelFactory

class CreateAlbumFragment(
    private val repository: AlbumRepository = AlbumRepositoryImpl()
) : Fragment() {

    private val viewModel: AlbumCreateViewModel by viewModels {
        AlbumCreateViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputName = view.findViewById<EditText>(R.id.inputName)
        val inputCover = view.findViewById<EditText>(R.id.inputCover)
        val inputRelease = view.findViewById<EditText>(R.id.inputReleaseDate)
        val inputGenre = view.findViewById<EditText>(R.id.inputGenre)
        val inputRecordLabel = view.findViewById<EditText>(R.id.inputRecordLabel)
        val inputDescription = view.findViewById<EditText>(R.id.inputDescription)
        val btnCreate = view.findViewById<Button>(R.id.btnCreateAlbum)
        val progress = view.findViewById<ProgressBar>(R.id.progressBarCreate)
        val textError = view.findViewById<TextView>(R.id.textErrorCreate)

        btnCreate.setOnClickListener {
            val request = CreateAlbumRequest(
                name = inputName.text.toString(),
                cover = inputCover.text.toString().ifBlank { null },
                releaseDate = inputRelease.text.toString(),
                description = inputDescription.text.toString().ifBlank { null },
                genre = inputGenre.text.toString(),
                recordLabel = inputRecordLabel.text.toString().ifBlank { null },
                performerIds = null,
                tracks = listOf()
            )
            viewModel.createAlbum(request)
        }

        viewModel.uiState.observe(viewLifecycleOwner){ state ->
            when(state){
                is AlbumCreateUiState.Idle -> {
                    progress.visibility = View.GONE
                    textError.visibility = View.GONE
                }
                is AlbumCreateUiState.Loading -> {
                    progress.visibility = View.VISIBLE
                    textError.visibility = View.GONE
                }
                is AlbumCreateUiState.Success -> {
                    progress.visibility = View.GONE
                    textError.visibility = View.GONE
                    Toast.makeText(requireContext(), "Álbum creado", Toast.LENGTH_SHORT).show()
                }
                is AlbumCreateUiState.Error -> {
                    progress.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.message
                }
                is AlbumCreateUiState.ValidationError -> {
                    progress.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.fieldErrors.entries.joinToString("; ") { "${it.key}: ${it.value}" }
                }
            }
        }
    }
}
