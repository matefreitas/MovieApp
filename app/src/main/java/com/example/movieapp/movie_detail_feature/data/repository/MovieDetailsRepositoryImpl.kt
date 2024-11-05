package com.example.movieapp.movie_detail_feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieDetails
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

    override suspend fun getMoviesSimilar(
        movieInt: Int,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getSimilarMoviesPagingSource(movieInt)
            }
        ).flow
    }
}