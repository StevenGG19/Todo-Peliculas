package com.steven.todopeliculas.ui.searchmovies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.data.model.Movie
import com.steven.todopeliculas.databinding.SearchMovieListBinding

class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.SearchViewHolder>() {
    private var movieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemBinding =
            SearchMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = movieList.size

    fun setData(data: List<Movie>) {
        movieList.clear()
        movieList.addAll(data)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(val binding: SearchMovieListBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            Glide.with(context).load(AppConstants.IMAGE_URL + item.poster_path).centerCrop()
                .into(binding.imgSearchMovie)
            binding.txtTitleSearch.text = item.title
            binding.txtLanguage.text = "Language ${item.original_language}"
            binding.txtReleased.text = "Released ${item.release_date}"
            val userCount = item.vote_average * 10
            binding.pbScore.progress = userCount.toInt()
            binding.txtProgress.text = "${userCount.toInt()}%"
        }
    }
}