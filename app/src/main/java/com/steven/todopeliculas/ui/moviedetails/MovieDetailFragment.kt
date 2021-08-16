package com.steven.todopeliculas.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.steven.todopeliculas.R
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailFragmentArgs>()

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
        Glide.with(requireContext()).load(AppConstants.IMAGE_URL + args.posterImageUrl)
            .centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load(AppConstants.IMAGE_URL + args.backgroundImageUrl)
            .centerCrop().into(binding.imgBackground)
        binding.txtMovieTitle.text = args.title
        binding.txtDescription.text = args.overview
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtReleased.text = "Released ${args.releaseDate}"
    }
}