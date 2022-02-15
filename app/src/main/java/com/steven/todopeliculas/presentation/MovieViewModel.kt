package com.steven.todopeliculas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.data.local.entities.FavoriteMovieEntity
import com.steven.todopeliculas.data.model.MovieList
import com.steven.todopeliculas.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

    /*las supen fun necesitan un hilo donde ejecutarse en este caso utilizamos el
    Dispatchers.IO que sirve para ejecutar procesos que vienen del servidor
     */
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(
                Resource.Success(
                    Triple(
                        repo.getUpcomingMovie(),
                        repo.getTopRatedMovie(),
                        repo.getPopularMovie()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    // MovieDetailFragment and FavoriteMoviesFragment
    val favoriteMovieList: LiveData<List<FavoriteMovieEntity>> = repo.dataSourceLocal.getFavoriteMovies()

    fun saveFavoriteMovie(movie: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataSourceLocal.saveFavoriteMovie(movie)
        }
    }

    fun deleteFavoriteMovie(movie: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataSourceLocal.deleteFavoriteMovie(movie)
        }
    }

    // SearchMovie
    fun foundMovies(movie: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(repo.searchMovies(movie))
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    // Trending movies
    fun getTrending(time: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(repo.getTrending(time))
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}