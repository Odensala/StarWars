package com.odensala.starwars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.odensala.starwars.databinding.FilmItemBinding
import com.odensala.starwars.model.Result

class FilmsAdapter : ListAdapter<Result, FilmsAdapter.FilmsViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((Result) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(currentItem) }
        }
    }

    /**
     * Set our own clickListener so it's accessible outside the adapter with safeargs
     */
    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }

    class FilmsViewHolder(private val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Result) {
            binding.apply {
                textViewFilmName.text = film.title
                textViewFilmReleaseDate.text = film.release_date
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result) =
        oldItem.url == newItem.url


    override fun areContentsTheSame(oldItem: Result, newItem: Result) =
        oldItem == newItem
}

