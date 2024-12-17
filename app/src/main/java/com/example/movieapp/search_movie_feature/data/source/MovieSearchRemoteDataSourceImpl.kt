package com.example.movieapp.search_movie_feature.data.source

import com.example.movieapp.core.data.remote.MovieService
import com.example.movieapp.core.data.remote.response.SearchResponse
import com.example.movieapp.core.domain.model.MovieSearchPaging
import com.example.movieapp.core.paging.MovieSearchPaggingSource
import com.example.movieapp.search_movie_feature.data.mapper.toMovieSearch
import com.example.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieSearchRemoteDataSource {
    override fun getSearchMoviePaggingSource(query: String): MovieSearchPaggingSource {
        return MovieSearchPaggingSource(query = query, remoteDataSource = this)
    }

    override suspend fun getSearchMovies(
        page: Int,
        query: String
    ): MovieSearchPaging {
        val response = service.searchMovie(page = page, query = query)
        return MovieSearchPaging(
            page = response.page,
            totalPages = response.totalPages,
            totalResults = response.totalResults,
            movies = response.results.map { it.toMovieSearch() }
        )
    }
}