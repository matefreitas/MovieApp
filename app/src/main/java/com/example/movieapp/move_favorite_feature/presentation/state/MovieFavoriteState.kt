package com.example.movieapp.move_favorite_feature.presentation.state

import com.example.movieapp.core.domain.model.Movie

data class MovieFavoriteState(
    val movies: List<Movie> = emptyList()
)
