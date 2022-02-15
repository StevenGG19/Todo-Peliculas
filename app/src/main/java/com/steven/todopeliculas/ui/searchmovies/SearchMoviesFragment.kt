package com.steven.todopeliculas.ui.searchmovies

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.steven.todopeliculas.R
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.core.hide
import com.steven.todopeliculas.databinding.FragmentSearchMoviesBinding
import com.steven.todopeliculas.presentation.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentSearchMoviesBinding? = null
    private val binding get() = _binding!!
    private val adapter: SearchMoviesAdapter by lazy { SearchMoviesAdapter() }
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        binding.rvSearch.adapter = adapter
        getTrending("day")
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.day) {
                getTrending("day")
            } else if (checkedId == R.id.week) {
                getTrending("week")
            }
        }
        binding.searchMovie.setOnQueryTextListener(this)
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchMovie(it)   
        }
        return true
    }

    private fun searchMovie(query: String) {
        viewModel.foundMovies(query).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.txtTrending.text = "Search Results"
                    binding.chipGroup.hide()
                    adapter.setData(result.data.results)
                    hideKeyboardFrom()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Movie not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun hideKeyboardFrom() {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun getTrending(time: String) {
        viewModel.getTrending(time).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.setData(result.data.results)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}