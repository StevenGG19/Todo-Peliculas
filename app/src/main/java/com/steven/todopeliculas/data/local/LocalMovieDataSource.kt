package com.steven.todopeliculas.data.local

import androidx.lifecycle.LiveData
import com.steven.todopeliculas.data.model.FavoriteMovie
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
    
    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>> = movieDao.getAllFavoriteMovies()

    suspend fun saveMovie(movieEntity: MovieEntity) = movieDao.saveMovie(movieEntity)

    suspend fun saveFavoriteMovie(movie: FavoriteMovie) = movieDao.saveFavoriteMovie(movie)

    suspend fun deleteFavoriteMovie(movie: FavoriteMovie) = movieDao.deleteFavoriteMovie(movie)
}