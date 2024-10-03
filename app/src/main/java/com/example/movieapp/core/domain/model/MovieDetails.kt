package com.example.movieapp.core.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val overview: String?,
    val backdropPathUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val duration: Int,
    val voteCount: Int
)
