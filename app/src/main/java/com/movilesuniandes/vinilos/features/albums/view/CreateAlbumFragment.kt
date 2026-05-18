package com.movilesuniandes.vinilos.features.albums.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import coil.load
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepository
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepositoryImpl
import com.movilesuniandes.vinilos.features.albums.model.CreateAlbumRequest
import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateViewModel
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumCreateViewModelFactory
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        val inputGenre = view.findViewById<MaterialAutoCompleteTextView>(R.id.inputGenre)
        val inputRecordLabel = view.findViewById<MaterialAutoCompleteTextView>(R.id.inputRecordLabel)
        val inputDescription = view.findViewById<EditText>(R.id.inputDescription)
        val btnCreate = view.findViewById<Button>(R.id.btnCreateAlbum)
        val progress = view.findViewById<ProgressBar>(R.id.progressBarCreate)
        val textError = view.findViewById<TextView>(R.id.textErrorCreate)
        val createTypeGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.createTypeGroup)
        val albumFormContainer = view.findViewById<LinearLayout>(R.id.albumFormContainer)
        val textComingSoon = view.findViewById<TextView>(R.id.textComingSoon)
        val cardFormContainer = view.findViewById<View>(R.id.cardFormContainer)
        val cardSuccessContainer = view.findViewById<View>(R.id.cardSuccessContainer)
        val imageSuccessCover = view.findViewById<ImageView>(R.id.imageSuccessCover)
        val textSuccessAlbumName = view.findViewById<TextView>(R.id.textSuccessAlbumName)
        val textSuccessAlbumMeta = view.findViewById<TextView>(R.id.textSuccessAlbumMeta)
        val textSuccessGenreValue = view.findViewById<TextView>(R.id.textSuccessGenreValue)
        val textSuccessYearValue = view.findViewById<TextView>(R.id.textSuccessYearValue)
        val textSuccessLabelValue = view.findViewById<TextView>(R.id.textSuccessLabelValue)

        val genres = listOf("Classical", "Salsa", "Rock", "Folk")
        val recordLabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        var selectedGenre: String? = null
        var selectedRecordLabel: String? = null
        inputGenre.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, genres)
        )
        inputGenre.setOnItemClickListener { _, _, position, _ ->
            selectedGenre = genres[position]
            inputGenre.setText(selectedGenre, false)
        }
        inputRecordLabel.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, recordLabels)
        )
        inputRecordLabel.setOnItemClickListener { _, _, position, _ ->
            selectedRecordLabel = recordLabels[position]
            inputRecordLabel.setText(selectedRecordLabel, false)
        }

        var selectedReleaseDateIso: String? = null
        val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun formatYear(dateIso: String): String {
            return dateIso.substringBefore("-")
        }

        fun openDatePicker() {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.create_release_hint))
                .build()

            picker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = Instant.ofEpochMilli(selection)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                selectedReleaseDateIso = LocalDateTime.of(selectedDate, java.time.LocalTime.MIDNIGHT)
                    .format(isoFormatter)
                    .plus("Z")
                inputRelease.setText(selectedDate.format(displayFormatter))
            }

            picker.show(parentFragmentManager, "create_album_date_picker")
        }

        inputRelease.setOnClickListener { openDatePicker() }
        inputRelease.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) openDatePicker()
        }

        fun showAlbumForm() {
            cardFormContainer.visibility = View.VISIBLE
            cardSuccessContainer.visibility = View.GONE
            albumFormContainer.visibility = View.VISIBLE
            textComingSoon.visibility = View.GONE
        }

        fun showComingSoon() {
            cardFormContainer.visibility = View.GONE
            cardSuccessContainer.visibility = View.GONE
            albumFormContainer.visibility = View.GONE
            textComingSoon.visibility = View.VISIBLE
            textComingSoon.text = getString(R.string.create_coming_soon)
        }

        fun showSuccess(album: Album) {
            cardFormContainer.visibility = View.GONE
            albumFormContainer.visibility = View.GONE
            textComingSoon.visibility = View.GONE
            cardSuccessContainer.visibility = View.VISIBLE

            imageSuccessCover.load(album.cover) {
                crossfade(true)
                placeholder(R.drawable.ic_albums)
                error(R.drawable.ic_albums)
            }
            textSuccessAlbumName.text = album.name
            textSuccessAlbumMeta.text = "${album.genre} · ${formatYear(album.releaseDate)}"
            textSuccessGenreValue.text = album.genre
            textSuccessYearValue.text = formatYear(album.releaseDate)
            textSuccessLabelValue.text = album.recordLabel.ifBlank { getString(R.string.artist_not_available) }
        }

        createTypeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId) {
                R.id.btnCreateTypeAlbum -> showAlbumForm()
                R.id.btnCreateTypeArtist,
                R.id.btnCreateTypeCollection -> showComingSoon()
            }
        }

        showAlbumForm()

        btnCreate.setOnClickListener {
            if (createTypeGroup.checkedButtonId != R.id.btnCreateTypeAlbum) {
                textError.visibility = View.GONE
                textComingSoon.visibility = View.VISIBLE
                textComingSoon.text = getString(R.string.create_coming_soon)
                return@setOnClickListener
            }

            val request = CreateAlbumRequest(
                name = inputName.text.toString(),
                cover = inputCover.text.toString().ifBlank { null },
                releaseDate = selectedReleaseDateIso ?: inputRelease.text.toString(),
                description = inputDescription.text.toString().ifBlank { null },
                genre = selectedGenre ?: inputGenre.text.toString(),
                recordLabel = selectedRecordLabel ?: inputRecordLabel.text.toString(),
                performerIds = null,
                tracks = null
            )
            viewModel.createAlbum(request)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
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
                    showSuccess(state.album)
                }
                is AlbumCreateUiState.Error -> {
                    progress.visibility = View.GONE
                    cardSuccessContainer.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.message
                }
                is AlbumCreateUiState.ValidationError -> {
                    progress.visibility = View.GONE
                    cardSuccessContainer.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                    textError.text = state.fieldErrors.entries.joinToString("\n") { "${it.key}: ${it.value}" }
                }
            }
        }
    }
}
