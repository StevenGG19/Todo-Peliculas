package com.steven.todopeliculas.ui.favoritemovies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.application.OnMovieClickListener
import com.steven.todopeliculas.core.BaseViewHolder
import com.steven.todopeliculas.data.model.FavoriteMovie
import com.steven.todopeliculas.databinding.FavoriteMovieItemBinding

class FavoriteMovieAdapter(private val itemClickListener: OnMovieClickListener<FavoriteMovie>) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    private var listFavoriteMovies = ArrayList<FavoriteMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            FavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is FavoriteMovieAdapter.MoviesViewHolder -> holder.bind(listFavoriteMovies[position])
        }
    }

    override fun getItemCount() = listFavoriteMovies.size

    fun updateData(data: List<FavoriteMovie>) {
        listFavoriteMovies.clear()
        listFavoriteMovies.addAll(data)
        notifyDataSetChanged()
    }

    private inner class MoviesViewHolder(
        val binding: FavoriteMovieItemBinding,
        val context: Context
    ) : BaseViewHolder<FavoriteMovie>(binding.root) {
        override fun bind(item: FavoriteMovie) {
            Glide.with(context).load(AppConstants.IMAGE_URL + item.poster_path)
                .centerCrop().into(binding.imgFavoriteMovie)
            binding.txtTitleFavorite.text = item.title
            binding.txtLanguage.text = "Language ${item.original_language}"
            binding.txtRatingFavorite.text = "${item.vote_average} (${item.vote_count} Reviews)"
            binding.txtReleased.text = "Released ${item.release_date}"
            binding.imgFavorite.setOnClickListener {
                itemClickListener.onMovieClick(item)
            }
        }

    }
}