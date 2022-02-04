package com.steven.todopeliculas.data.local

import androidx.lifecycle.LiveData
import com.steven.todopeliculas.core.toMovieList
import com.steven.todopeliculas.data.local.entities.FavoriteMovieEntity
import com.steven.todopeliculas.data.local.entities.MovieEntity
import com.steven.todopeliculas.data.local.entities.UserEntity
import com.steven.todopeliculas.data.model.MovieList
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun getTopRatedMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "top" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }
    
    fun getFavoriteMovies(): LiveData<List<FavoriteMovieEntity>> = movieDao.getAllFavoriteMovies()

    suspend fun saveMovie(movieEntity: MovieEntity) = movieDao.saveMovie(movieEntity)

    suspend fun saveFavoriteMovie(movie: FavoriteMovieEntity) = movieDao.saveFavoriteMovie(movie)

    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity) = movieDao.deleteFavoriteMovie(movie)

    suspend fun saveUser(user: UserEntity) = movieDao.saveUser(user)

    fun getUser() = movieDao.getUser()

    suspend fun deleteUser() = movieDao.deleteUser()

    suspend fun updateUser(user: UserEntity) = movieDao.updateUser(user)
}