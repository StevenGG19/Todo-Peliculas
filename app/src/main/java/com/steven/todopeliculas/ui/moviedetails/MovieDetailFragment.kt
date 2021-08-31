package com.steven.todopeliculas.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.steven.todopeliculas.R
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.data.local.AppDatabase
import com.steven.todopeliculas.data.local.LocalMovieDataSource
import com.steven.todopeliculas.data.model.toFavoriteMovie
import com.steven.todopeliculas.databinding.FragmentMovieDetailBinding
import com.steven.todopeliculas.presentation.FavoriteMovieViewModel
import com.steven.todopeliculas.presentation.FavoriteMovieViewModelFactory

class MovieDetailFragment : DialogFragment() {
    private var isFavorite = false
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<FavoriteMovieViewModel> {
        FavoriteMovieViewModelFactory(
            LocalMovieDataSource(
                AppDatabase.getDatabase(requireContext()).movieDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_TodoPeliculas_light)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarMovieDetail.navigationIcon =
            ContextCompat.getDrawable(view.context, R.drawable.ic_arrow_back)
        binding.toolbarMovieDetail.setNavigationOnClickListener {
            dismiss()
        }

        findFavoriteMovie()

        binding.favorite.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteFavoriteMovie(args.toFavoriteMovie())
                binding.imgFavorite.setImageResource(R.drawable.ic_favorite_border)
                isFavorite = false
            } else {
                viewModel.saveFavoriteMovie(args.toFavoriteMovie())
            }
        }

        Glide.with(requireContext()).load(AppConstants.IMAGE_URL + args.posterImageUrl)
            .centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load(AppConstants.IMAGE_URL + args.backgroundImageUrl)
            .centerCrop().into(binding.imgBackground)
        binding.txtMovieTitle.text = args.title
        binding.txtDescription.text = args.overview
        binding.txtLanguage.text = getString(R.string.language, args.language)
        binding.txtRating.text = getString(R.string.reviews, args.voteAverage, args.voteCount)
        binding.txtReleased.text = getString(R.string.released, args.releaseDate)
    }

    private fun findFavoriteMovie() {
        viewModel.favoriteMovieList.observe(viewLifecycleOwner, { movieList ->
            movieList.forEach {
                if (it.id == args.id) {
                    binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite)
                    isFavorite = true
                    return@observe
                }
            }
        })
    }
}