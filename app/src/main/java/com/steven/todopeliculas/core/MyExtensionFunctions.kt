package com.steven.todopeliculas.core

import android.view.View
import com.steven.todopeliculas.data.local.entities.FavoriteMovieEntity
import com.steven.todopeliculas.data.local.entities.MovieEntity
import com.steven.todopeliculas.data.model.Movie
import com.steven.todopeliculas.data.model.MovieList

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Movie.isNull(): Boolean {
    return this.backdrop_path == null
}

fun Movie.toMovieEntity(type: String) = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path!!,
    this.original_language,
    this.original_title,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count,
    movie_type = type
)

fun MovieEntity.toMovie() = Movie(
    this.id,
    this.adult,
    this.backdrop_path,
    this.original_language,
    this.original_title,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count,
    this.movie_type
)

fun List<MovieEntity>.toMovieList(): MovieList {
    val list = mutableListOf<Movie>()

    this.forEach { movie ->
        list.add(movie.toMovie())
    }

    return MovieList(list)
}

fun Movie.toFavoriteMovie() = FavoriteMovieEntity(
    this.id,
    this.backdrop_path ?: "",
    this.original_language,
    this.overview,
    this.poster_path,
    this.release_date,
    this.title,
    this.vote_average.toFloat(),
    this.vote_count
)