package com.example.movieapp.movie_popular_feature.domain.source

import com.example.movieapp.core.data.remote.response.MovieResponse
import com.example.movieapp.core.paging.MoviePagingSource

interface MoviePopularRemoteDataSource {

    fun getPopularMoviesPagingSource(): MoviePagingSource

    suspend fun getPopularMovies(page: Int): MovieResponse
}