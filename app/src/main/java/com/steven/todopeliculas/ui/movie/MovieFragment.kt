package com.steven.todopeliculas.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.steven.todopeliculas.application.OnMovieClickListener
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.data.local.AppDatabase
import com.steven.todopeliculas.data.local.LocalMovieDataSource
import com.steven.todopeliculas.data.model.Movie
import com.steven.todopeliculas.data.remote.RemoteMovieDataSource
import com.steven.todopeliculas.databinding.FragmentMovieBinding
import com.steven.todopeliculas.presentation.MovieViewModel
import com.steven.todopeliculas.presentation.MovieViewModelFactory
import com.steven.todopeliculas.repository.MovieRepositoryImpl
import com.steven.todopeliculas.repository.RetrofitClient
import com.steven.todopeliculas.ui.movie.adapters.MovieAdapter
import com.steven.todopeliculas.ui.movie.adapters.concat.PopularConcatAdapter
import com.steven.todopeliculas.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.steven.todopeliculas.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(), OnMovieClickListener<Movie> {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var concatAdapter: ConcatAdapter

    // Inyeccion de dependencias manual
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, { result ->

            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0, UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1, TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2, PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("Error", "${result.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path!!,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date,
            movie.id
        )
        findNavController().navigate(action)
    }

}