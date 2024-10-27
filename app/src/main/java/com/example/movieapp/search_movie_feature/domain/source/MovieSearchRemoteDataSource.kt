package com.example.movieapp.search_movie_feature.domain.source

import com.example.movieapp.core.data.remote.response.SearchResponse
import com.example.movieapp.core.paging.MovieSearchPaggingSource

interface MovieSearchRemoteDataSource {
    fun getSearchMoviePaggingSource(query: String): MovieSearchPaggingSource
    suspend fun getSearchMovies(page: Int, query: String): SearchResponse
}