package com.example.movieapp.movie_popular_feature.data.repository

import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.paging.MoviePagingSource
import com.example.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import com.example.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource

class MoviePopularRepositoryImpl constructor(
    private val remoteDataSource: MoviePopularRemoteDataSource
): MoviePopularRepository {
    override fun getPopularMovies(): PagingSource<Int, Movie>{
        return MoviePagingSource(remoteDataSource)
    }
}