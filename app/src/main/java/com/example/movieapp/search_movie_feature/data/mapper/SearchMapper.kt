package com.example.movieapp.search_movie_feature.data.mapper

import com.example.movieapp.core.data.remote.model.SearchResult
import com.example.movieapp.core.domain.model.MovieSearch
import com.example.movieapp.core.util.toPostUrl

fun List<SearchResult>.toMovieSearch() = map { searchResult ->
    MovieSearch(
        id = searchResult.id,
        imageUrl = searchResult.posterPath.toPostUrl(),
        voteAvarege = searchResult.voteAverage
    )
}