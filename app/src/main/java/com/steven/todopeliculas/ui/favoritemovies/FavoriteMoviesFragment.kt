package com.steven.todopeliculas.ui.favoritemovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.steven.todopeliculas.application.OnMovieClickListener
import com.steven.todopeliculas.data.local.entities.FavoriteMovieEntity
import com.steven.todopeliculas.databinding.FragmentFavoriteMoviesBinding
import com.steven.todopeliculas.presentation.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment(), OnMovieClickListener<FavoriteMovieEntity> {
    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var concatAdapter: FavoriteMovieAdapter
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        concatAdapter = FavoriteMovieAdapter(this)
        binding.rvFavoriteMovies.adapter = concatAdapter
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.favoriteMovieList.observe(viewLifecycleOwner, { movie ->
            if(movie.isEmpty()) {
                binding.rlNoFavorites.visibility = View.VISIBLE
            }
            concatAdapter.updateData(movie)
        })
    }

    override fun onMovieClick(movie: FavoriteMovieEntity) {
        viewModel.deleteFavoriteMovie(movie)
    }
}