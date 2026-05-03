package com.movilesuniandes.vinilos.features.collector.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.collector.model.Collector

class CollectorAdapter(private val onItemClick: (Int) -> Unit):
    ListAdapter<Collector, CollectorAdapter.CollectorViewHolder>(CollectorDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collector, parent, false)
        return CollectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        val collector= getItem(position)
        holder.bind(collector)
        holder.itemView.setOnClickListener { onItemClick(collector.id) }
    }

    class CollectorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val nameText: TextView= itemView.findViewById(R.id.textCollectorName)
        private val emailText: TextView = itemView.findViewById(R.id.textCollectorEmail)
        private val telephoneText: TextView = itemView.findViewById(R.id.textCollectorTelephone)
        fun bind(collector: Collector){
            nameText.text = collector.name
            emailText.text= collector.email
            telephoneText.text= collector.telephone
        }

    }

    private class CollectorDiffCallback: DiffUtil.ItemCallback<Collector>(){
        override fun areItemsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem == newItem
        }
    }
}
