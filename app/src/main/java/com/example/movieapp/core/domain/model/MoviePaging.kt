package com.example.movieapp.core.domain.model

data class MoviePaging(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val movies: List<Movie>
)