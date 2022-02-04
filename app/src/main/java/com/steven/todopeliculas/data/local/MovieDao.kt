package com.steven.todopeliculas.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.steven.todopeliculas.data.local.entities.FavoriteMovieEntity
import com.steven.todopeliculas.data.local.entities.MovieEntity
import com.steven.todopeliculas.data.local.entities.UserEntity

@Dao
interface MovieDao {

    //Movies
    @Query("SELECT * FROM MovieEntity")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM FavoriteMovieEntity")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(movie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)

    //User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity")
    fun getUser(): LiveData<UserEntity>

    @Query("DELETE FROM UserEntity")
    suspend fun deleteUser()

    @Update
    suspend fun updateUser(user: UserEntity)
}