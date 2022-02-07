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
import com.steven.todopeliculas.data.model.Movie
import com.steven.todopeliculas.databinding.FragmentMovieBinding
import com.steven.todopeliculas.presentation.MovieViewModel
import com.steven.todopeliculas.ui.movie.adapters.MovieAdapter
import com.steven.todopeliculas.ui.movie.adapters.concat.PopularConcatAdapter
import com.steven.todopeliculas.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.steven.todopeliculas.ui.movie.adapters.concat.UpcomingConcatAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), OnMovieClickListener<Movie> {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var concatAdapter: ConcatAdapter
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) { result ->
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
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }


}