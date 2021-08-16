package com.steven.todopeliculas.ui.movie.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.steven.todopeliculas.core.BaseConcatHolder
import com.steven.todopeliculas.databinding.TopRatedMoviesRowBinding
import com.steven.todopeliculas.ui.movie.adapters.MovieAdapter

class TopRatedConcatAdapter(private val movieAdapter: MovieAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemView = TopRatedMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        if (holder is ConcatViewHolder) holder.bind(movieAdapter)
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: TopRatedMoviesRowBinding): BaseConcatHolder<MovieAdapter>(binding.root) {
        override fun bind(adapter: MovieAdapter) {
            binding.rvTopRatedMovies.adapter = adapter
        }
    }
}