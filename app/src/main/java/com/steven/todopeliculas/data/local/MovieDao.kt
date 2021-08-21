package com.steven.todopeliculas.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.steven.todopeliculas.data.model.FavoriteMovie
import com.steven.todopeliculas.data.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM FavoriteMovie")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie)

}