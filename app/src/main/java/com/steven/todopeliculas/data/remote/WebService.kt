package com.steven.todopeliculas.data.remote

import com.steven.todopeliculas.data.model.MovieList
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