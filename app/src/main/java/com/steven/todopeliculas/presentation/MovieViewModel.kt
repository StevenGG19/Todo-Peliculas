package com.steven.todopeliculas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository): ViewModel() {

    /*las supen fun necesitan un hilo donde ejecutarse en este caso utilizamos el
    Dispatchers.IO que sirve para ejecutar procesos que vienen del servidor
     */

    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(Triple(repo.getUpcomingMovie(), repo.getTopRatedMovie(), repo.getPopularMovie())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}