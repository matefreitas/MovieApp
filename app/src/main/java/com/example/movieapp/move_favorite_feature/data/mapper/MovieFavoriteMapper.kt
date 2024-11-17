package com.example.movieapp.move_favorite_feature.data.mapper

import com.example.movieapp.core.data.local.entity.MovieEntity
import com.example.movieapp.core.domain.model.Movie

fun List<MovieEntity>.toMovies() = map { movieEntity ->
    Movie(
        id = movieEntity.movieId,
        title = movieEntity.title,
        imageUrl = movieEntity.imageUrl
    )
}

fun Movie.toMovieEntity(): MovieEntity{
    return MovieEntity(
        movieId = id,
        title = title,
        imageUrl = imageUrl
    )
}