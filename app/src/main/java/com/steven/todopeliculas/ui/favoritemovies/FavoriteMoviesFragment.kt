package com.steven.todopeliculas.ui.favoritemovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.steven.todopeliculas.application.OnMovieClickListener
import com.steven.todopeliculas.data.local.AppDatabase
import com.steven.todopeliculas.data.local.LocalMovieDataSource
import com.steven.todopeliculas.data.model.FavoriteMovie
import com.steven.todopeliculas.databinding.FragmentFavoriteMoviesBinding
import com.steven.todopeliculas.presentation.FavoriteMovieViewModel
import com.steven.todopeliculas.presentation.FavoriteMovieViewModelFactory

class FavoriteMoviesFragment : Fragment(), OnMovieClickListener<FavoriteMovie> {
    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var concatAdapter: FavoriteMovieAdapter
    private val viewModel by viewModels<FavoriteMovieViewModel> {
        FavoriteMovieViewModelFactory(
            LocalMovieDataSource(
                AppDatabase.getDatabase(requireContext()).movieDao()
            )
        )
    }

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
            concatAdapter.updateData(movie)
        })
    }

    override fun onMovieClick(movie: FavoriteMovie) {
        viewModel.deleteFavoriteMovie(movie)
    }


}