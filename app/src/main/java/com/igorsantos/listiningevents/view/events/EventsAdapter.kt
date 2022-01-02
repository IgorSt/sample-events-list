package com.igorsantos.listiningevents.view.events

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igorsantos.listiningevents.R
import com.igorsantos.listiningevents.databinding.ItemEventBinding
import com.igorsantos.listiningevents.domain.model.Events

class EventsAdapter : ListAdapter<Events, EventsAdapter.EventVH>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Events>() {
            override fun areItemsTheSame(oldItem: Events, newItem: Events): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Events, newItem: Events): Boolean {
                return oldItem == newItem
            }

        }
    }

    class EventVH(
        private val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setup(item: Events) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.image)
                    .error(R.drawable.erro_404)
                    .into(binding.imagePresentation)
                title.text = item.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventVH {
        return EventVH(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventVH, position: Int) {
        holder.setup(getItem(position))
    }
}