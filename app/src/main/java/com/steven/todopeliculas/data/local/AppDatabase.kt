package com.steven.todopeliculas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.steven.todopeliculas.data.local.entities.FavoriteMovieEntity
import com.steven.todopeliculas.data.local.entities.MovieEntity
import com.steven.todopeliculas.data.local.entities.UserEntity

@Database(
    entities = [MovieEntity::class, FavoriteMovieEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}