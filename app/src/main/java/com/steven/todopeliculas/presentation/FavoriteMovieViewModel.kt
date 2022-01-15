package com.steven.todopeliculas.presentation

import androidx.lifecycle.*
import com.steven.todopeliculas.data.local.LocalMovieDataSource
import com.steven.todopeliculas.data.model.FavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(private val repo: LocalMovieDataSource) : ViewModel() {

    val favoriteMovieList: LiveData<List<FavoriteMovie>> = repo.getFavoriteMovies()

    fun saveFavoriteMovie(movie: FavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveFavoriteMovie(movie)
        }
    }

    fun deleteFavoriteMovie(movie: FavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFavoriteMovie(movie)
        }
    }

}


class FavoriteMovieViewModelFactory(private val repo: LocalMovieDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LocalMovieDataSource::class.java).newInstance(repo)
    }
}