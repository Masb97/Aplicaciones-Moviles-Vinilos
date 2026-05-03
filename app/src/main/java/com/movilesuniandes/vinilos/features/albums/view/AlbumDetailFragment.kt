package com.movilesuniandes.vinilos.features.albums.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.model.Album
import com.movilesuniandes.vinilos.features.albums.model.AlbumRepositoryImpl
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumDetailUiState
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumDetailViewModel
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumDetailViewModelFactory
import com.movilesuniandes.vinilos.features.albums.viewmodel.AlbumViewModel

class AlbumDetailFragment: Fragment() {
    private val viewModel: AlbumDetailViewModel by viewModels {
        AlbumDetailViewModelFactory(AlbumRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar= view.findViewById<ProgressBar>(R.id.progressBar)
        val textError= view.findViewById<TextView>(R.id.textError)
        val contentGroup= view.findViewById<androidx.constraintlayout.widget.Group>(R.id.contentGroup)

        val albumId = arguments?.getInt("albumId")?: -1
        if (albumId != -1){
            viewModel.loadAlbum(albumId)
        }

        viewModel.uiState.observe(viewLifecycleOwner){state->
            when(state){
                is AlbumDetailUiState.Loading ->{
                    progressBar.visibility= View.VISIBLE
                    contentGroup.visibility= View.GONE
                    textError.visibility= View.GONE
                }

                is AlbumDetailUiState.Success ->{
                    progressBar.visibility= View.GONE
                    contentGroup.visibility= View.VISIBLE
                    textError.visibility= View.GONE
                    populateUI(state.album)
                }

                is AlbumDetailUiState.Error ->{
                    progressBar.visibility= View.GONE
                    contentGroup.visibility= View.GONE
                    textError.visibility= View.VISIBLE
                    textError.text= state.message
                }

            }
        }



    }
    private fun populateUI(album: Album){
        view?.apply {
            findViewById<ImageView>(R.id.imageAlbumCover).load(album.cover)
            findViewById<TextView>(R.id.textAlbumName).text= album.name
            findViewById<TextView>(R.id.textAlbumDescription).text= album.description
        }
    }

}