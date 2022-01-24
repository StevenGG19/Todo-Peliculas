package com.steven.todopeliculas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.steven.todopeliculas.data.model.FavoriteMovie
import com.steven.todopeliculas.data.model.MovieEntity

@Database(
    entities = [MovieEntity::class, FavoriteMovie::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}