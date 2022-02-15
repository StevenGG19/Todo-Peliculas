package com.steven.todopeliculas.data.remote

import com.steven.todopeliculas.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(@Query("api_key") apikey: String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(@Query("api_key") apikey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("api_key") apikey: String): MovieList

    @GET("search/movie?page=1&include_adult=false")
    suspend fun searchMovie(
        @Query("api_key") apikey: String,
        @Query("query") movie: String
    ): MovieList

    @GET("trending/movie/{time}")
    suspend fun getTrending(@Path("time") time: String, @Query("api_key") apikey: String): MovieList
}