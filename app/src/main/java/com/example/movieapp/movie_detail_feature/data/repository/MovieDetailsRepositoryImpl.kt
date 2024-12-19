package com.example.movieapp.movie_detail_feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieDetails
import com.example.movieapp.core.paging.MovieSimilarPagingSource
import com.example.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import com.example.movieapp.movie_detail_feature.domain.source.MovieDetailsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailsRemoteDataSource
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movieInt: Int): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId = movieInt)
    }

    override fun getMoviesSimilar(
        movieInt: Int
    ): PagingSource<Int, Movie> {
        return MovieSimilarPagingSource(movieId = movieInt, remoteDataSource = remoteDataSource)
    }
}