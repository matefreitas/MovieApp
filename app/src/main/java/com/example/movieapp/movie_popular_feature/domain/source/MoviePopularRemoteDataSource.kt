package com.example.movieapp.movie_popular_feature.domain.source

import com.example.movieapp.core.domain.model.MoviePaging
import com.example.movieapp.core.paging.MoviePagingSource

interface MoviePopularRemoteDataSource {

    fun getPopularMoviesPagingSource(): MoviePagingSource

    suspend fun getPopularMovies(page: Int): MoviePaging
}