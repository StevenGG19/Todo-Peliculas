package com.steven.todopeliculas.di

import android.content.Context
import androidx.room.Room
import com.steven.todopeliculas.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "movie_table"
    ).build()

    @Provides
    @Singleton
    fun provideDao(dataBase: AppDatabase) = dataBase.movieDao()
}