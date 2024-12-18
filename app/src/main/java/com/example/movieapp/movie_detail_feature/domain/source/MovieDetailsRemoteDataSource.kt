package com.example.movieapp.movie_detail_feature.domain.source

import com.example.movieapp.core.domain.model.MovieDetails
import com.example.movieapp.core.domain.model.MoviePaging
import com.example.movieapp.core.paging.MovieSimilarPagingSource

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMoviesSimilar(page: Int, movieId: Int): MoviePaging
    fun getSimilarMoviesPagingSource(movieId: Int): MovieSimilarPagingSource
}

