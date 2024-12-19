package com.example.movieapp.search_movie_feature.data.repository

import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.MovieSearch
import com.example.movieapp.core.paging.MovieSearchPaggingSource
import com.example.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import com.example.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieSearchRemoteDataSource
): MovieSearchRepository {
    override fun getSearchMovies(
        query: String
    ): PagingSource<Int, MovieSearch> {
        return MovieSearchPaggingSource(query,remoteDataSource)
    }
}