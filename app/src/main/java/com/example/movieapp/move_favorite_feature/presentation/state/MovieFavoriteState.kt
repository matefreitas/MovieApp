package com.example.movieapp.move_favorite_feature.presentation.state

import com.example.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieFavoriteState(
    val movies: Flow<List<Movie>> = emptyFlow()
)
