package com.steven.todopeliculas.repository

import com.google.gson.GsonBuilder
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.data.model.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(@Query("api_key") apikey: String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(@Query("api_key") apikey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("api_key") apikey: String): MovieList
}

object RetrofitClient {
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}