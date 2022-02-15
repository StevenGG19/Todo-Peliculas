package com.steven.todopeliculas.data.remote

import com.steven.todopeliculas.application.AppConstants.API_KEY
import com.steven.todopeliculas.data.model.MovieList
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val webService: WebService) {
    
    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovie(API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovie(API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovie(API_KEY)

    suspend fun searchMovies(movie: String): MovieList = webService.searchMovie(API_KEY, movie)

    suspend fun getTrending(time: String): MovieList = webService.getTrending(time, API_KEY)
}