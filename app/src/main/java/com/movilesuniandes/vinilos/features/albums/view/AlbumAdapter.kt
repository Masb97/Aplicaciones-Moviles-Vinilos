package com.movilesuniandes.vinilos.features.albums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.model.Album

class AlbumAdapter : ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coverImage: ShapeableImageView = itemView.findViewById(R.id.imageAlbumCover)
        private val nameText: TextView = itemView.findViewById(R.id.textAlbumName)
        private val recordLabelText: TextView = itemView.findViewById(R.id.textRecordLabel)
        private val descriptionText: TextView = itemView.findViewById(R.id.textAlbumDescription)
        private val genreText: TextView = itemView.findViewById(R.id.textAlbumGenre)

        fun bind(album: Album) {
            coverImage.load(album.cover) {
                crossfade(true)
                placeholder(R.drawable.ic_albums)
                error(R.drawable.ic_albums)
            }
            nameText.text = album.name
            recordLabelText.text = album.recordLabel
            descriptionText.text = album.description
            genreText.text = album.genre
        }
    }

    private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
    }
}
