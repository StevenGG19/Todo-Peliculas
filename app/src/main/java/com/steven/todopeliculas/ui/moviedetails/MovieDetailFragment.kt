package com.steven.todopeliculas.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.steven.todopeliculas.R
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.core.toFavoriteMovie
import com.steven.todopeliculas.databinding.FragmentMovieDetailBinding
import com.steven.todopeliculas.presentation.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : DialogFragment() {
    private var isFavorite = false
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_TodoPeliculas_light)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.animation
        binding.toolbarMovieDetail.navigationIcon =
            ContextCompat.getDrawable(view.context, R.drawable.ic_arrow_back)
        binding.toolbarMovieDetail.setNavigationOnClickListener {
            dismiss()
        }

        findFavoriteMovie()

        binding.favorite.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite()
            } else {
                saveFavorite()
            }
        }

        Glide.with(requireContext()).load(AppConstants.IMAGE_URL + args.movie.poster_path)
            .centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load(AppConstants.IMAGE_URL + args.movie.backdrop_path)
            .centerCrop().into(binding.imgBackground)
        binding.txtMovieTitle.text = args.movie.title
        binding.txtDescription.text = args.movie.overview
        binding.txtLanguage.text = getString(R.string.language, args.movie.original_language)
        binding.txtRating.text = getString(R.string.reviews, args.movie.vote_average, args.movie.vote_count)
        binding.txtReleased.text = getString(R.string.released, args.movie.release_date)
    }

    private fun saveFavorite() {
        viewModel.saveFavoriteMovie(args.movie.toFavoriteMovie())
    }

    private fun removeFromFavorite() {
        viewModel.deleteFavoriteMovie(args.movie.toFavoriteMovie())
        changeIcon(binding.imgFavorite, R.drawable.ic_favorite_border)
        isFavorite = false
    }

    private fun findFavoriteMovie() {
        viewModel.favoriteMovieList.observe(viewLifecycleOwner, { movieList ->
            movieList.forEach {
                if (it.id == args.movie.id) {
                    changeIcon(binding.imgFavorite, R.drawable.ic_baseline_favorite)
                    isFavorite = true
                    return@observe
                }
            }
        })
    }

    private fun changeIcon(image: ImageView, icBaselineFavorite: Int) {
        image.setImageResource(icBaselineFavorite)
    }

}