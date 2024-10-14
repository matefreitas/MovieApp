package com.example.movieapp.movie_popular_feature.data.mapper

import com.example.movieapp.core.data.remote.model.MovieResult
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.util.toPostUrl

fun List<MovieResult>.toMovie() = map{ movieResult ->
    Movie(
        id = movieResult.id,
        title = movieResult.title,
        voteAverage = movieResult.voteAverage,
        imageUrl = movieResult.posterPath.toPostUrl() ?: ""
    )
}