package com.steven.todopeliculas.application

interface OnMovieClickListener<T> {
    fun onMovieClick(movie: T)
}