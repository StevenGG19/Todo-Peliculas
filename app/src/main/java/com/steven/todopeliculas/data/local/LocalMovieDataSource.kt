package com.steven.todopeliculas.data.local

import com.steven.todopeliculas.data.model.MovieEntity
import com.steven.todopeliculas.data.model.MovieList
import com.steven.todopeliculas.data.model.toMovieList

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun getTopRatedMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "top" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movieEntity: MovieEntity) = movieDao.saveMovie(movieEntity)
}