package com.movilesuniandes.vinilos.features.collector.view

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
import com.movilesuniandes.vinilos.features.collector.model.CollectorPerformer

class FavoritePerformerAdapter :
    ListAdapter<CollectorPerformer, FavoritePerformerAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_performer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ShapeableImageView = itemView.findViewById(R.id.imagePerformer)
        private val name: TextView = itemView.findViewById(R.id.textPerformerName)
        private val description: TextView = itemView.findViewById(R.id.textPerformerDescription)

        fun bind(performer: CollectorPerformer) {
            image.load(performer.image) {
                crossfade(true)
                placeholder(R.drawable.ic_artists)
                error(R.drawable.ic_artists)
            }
            image.contentDescription = itemView.context
                .getString(R.string.collector_performer_image_description, performer.name)
            name.text = performer.name
            description.text = performer.description
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<CollectorPerformer>() {
        override fun areItemsTheSame(oldItem: CollectorPerformer, newItem: CollectorPerformer) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CollectorPerformer, newItem: CollectorPerformer) =
            oldItem == newItem
    }
}
