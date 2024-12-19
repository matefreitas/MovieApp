package com.example.movieapp.movie_detail_feature.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieInt: Int): MovieDetails
    fun getMoviesSimilar(movieInt: Int): PagingSource<Int, Movie>
}