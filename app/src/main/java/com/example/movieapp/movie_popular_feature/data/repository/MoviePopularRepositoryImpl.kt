package com.example.movieapp.movie_popular_feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.paging.MoviePagingSource
import com.example.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import com.example.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource
import kotlinx.coroutines.flow.Flow

class MoviePopularRepositoryImpl constructor(
    private val remoteDataSource: MoviePopularRemoteDataSource
): MoviePopularRepository {
    override fun getPopularMovies(): PagingSource<Int, Movie>{
        return MoviePagingSource(remoteDataSource)
    }
}