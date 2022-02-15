package com.steven.todopeliculas.repository.movie

import com.steven.todopeliculas.core.InternetCheck
import com.steven.todopeliculas.core.isNull
import com.steven.todopeliculas.core.toMovieEntity
import com.steven.todopeliculas.data.local.LocalDataSource
import com.steven.todopeliculas.data.model.MovieList
import com.steven.todopeliculas.data.remote.RemoteMovieDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    remote: RemoteMovieDataSource,
    local: LocalDataSource
) {
    private val dataSource = remote
    val dataSourceLocal = local

    suspend fun getUpcomingMovie(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSource.getUpcomingMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    suspend fun getTopRatedMovie(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSource.getTopRatedMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("top"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }

    suspend fun getPopularMovie(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSource.getPopularMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }

    suspend fun searchMovies(movie: String): MovieList = dataSource.searchMovies(movie)

    suspend fun getTrending(time: String): MovieList = dataSource.getTrending(time)
}