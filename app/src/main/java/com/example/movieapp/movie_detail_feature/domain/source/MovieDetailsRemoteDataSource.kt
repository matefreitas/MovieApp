package com.example.movieapp.movie_detail_feature.domain.source

import com.example.movieapp.core.data.remote.response.MovieResponse
import com.example.movieapp.core.domain.model.MovieDetails
import com.example.movieapp.core.paging.MovieSimilarPagingSource

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMoviesSimilar(page: Int, movieId: Int): MovieResponse
    fun getSimilarMoviesPagingSource(movieId: Int): MovieSimilarPagingSource
}

