package com.movilesuniandes.vinilos.features.artists.view

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
import com.movilesuniandes.vinilos.features.artists.model.Artist
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind

class ArtistAdapter(
    private val onFavoriteClick: (Artist) -> Unit
) : ListAdapter<Artist, ArtistAdapter.ArtistViewHolder>(ArtistDiffCallback()) {

    private var favoriteArtistIds: Set<Int> = emptySet()

    fun updateFavorites(ids: Set<Int>) {
        favoriteArtistIds = ids
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = getItem(position)
        holder.bind(artist, favoriteArtistIds.contains(artist.id), onFavoriteClick)
    }

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageArtist: ShapeableImageView = itemView.findViewById(R.id.imageArtist)
        private val textArtistName: TextView = itemView.findViewById(R.id.textArtistName)
        private val textArtistSummary: TextView = itemView.findViewById(R.id.textArtistSummary)
        private val textArtistDescription: TextView = itemView.findViewById(R.id.textArtistDescription)
        private val textArtistKind: TextView = itemView.findViewById(R.id.textArtistKind)
        private val textFavorite: TextView = itemView.findViewById(R.id.textFavorite)

        fun bind(artist: Artist, isFavorite: Boolean, onFavoriteClick: (Artist) -> Unit) {
            imageArtist.load(artist.image) {
                crossfade(true)
                placeholder(R.drawable.ic_artists)
                error(R.drawable.ic_artists)
            }
            textArtistName.text = artist.name
            textArtistSummary.text = getDateSummary(artist)
            textArtistDescription.text = artist.description
            val isBand = artist.kind == ArtistKind.BANDA
            textArtistKind.text = if (isBand) {
                itemView.context.getString(R.string.artist_kind_band)
            } else {
                itemView.context.getString(R.string.artist_kind_musician)
            }
            textArtistKind.setBackgroundResource(
                if (isBand) R.drawable.bg_badge_band else R.drawable.bg_badge_musician
            )
            textFavorite.text = if (isFavorite) "★" else "☆"
            textFavorite.setTextColor(
                itemView.context.getColor(
                    if (isFavorite) R.color.amber else R.color.gray_divider
                )
            )
            textFavorite.contentDescription = if (isFavorite) {
                itemView.context.getString(R.string.favorite_remove_action)
            } else {
                itemView.context.getString(R.string.favorite_add_action)
            }
            textFavorite.setOnClickListener {
                onFavoriteClick(artist)
            }
        }

        private fun getDateSummary(artist: Artist): String {
            val context = itemView.context
            val dateRaw = artist.creationDate ?: artist.birthDate
            if (dateRaw.isNullOrBlank()) {
                return context.getString(R.string.artist_date_unavailable)
            }

            val dateFormatted = formatDisplayDate(dateRaw)
            return if (artist.kind == ArtistKind.BANDA) {
                context.getString(R.string.artist_creation_date_format, dateFormatted)
            } else {
                context.getString(R.string.artist_birth_date_format, dateFormatted)
            }
        }

        private fun formatDisplayDate(value: String): String {
            val datePart = value.substringBefore("T")
            val parts = datePart.split("-")
            if (parts.size != 3) return datePart
            return "${parts[2]}/${parts[1]}/${parts[0]}"
        }
    }

    private class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Artist, newItem: Artist) = oldItem == newItem
    }
}
