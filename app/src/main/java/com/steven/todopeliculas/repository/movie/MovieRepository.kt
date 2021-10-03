package com.steven.todopeliculas.repository.movie

import com.steven.todopeliculas.data.model.MovieList

interface MovieRepository {

    suspend fun getUpcomingMovie(): MovieList
    suspend fun getTopRatedMovie(): MovieList
    suspend fun getPopularMovie(): MovieList

}