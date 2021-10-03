package com.steven.todopeliculas.repository.movie

import com.steven.todopeliculas.core.InternetCheck
import com.steven.todopeliculas.data.local.LocalMovieDataSource
import com.steven.todopeliculas.data.model.MovieList
import com.steven.todopeliculas.data.model.isNull
import com.steven.todopeliculas.data.model.toMovieEntity
import com.steven.todopeliculas.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSource: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getUpcomingMovie(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSource.getUpcomingMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovie(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSource.getTopRatedMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("top"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovie(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSource.getPopularMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }
}