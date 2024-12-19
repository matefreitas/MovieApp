package com.example.movieapp.movie_detail_feature.domain.repository

import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieInt: Int): MovieDetails
    fun getMoviesSimilar(movieInt: Int): PagingSource<Int, Movie>
}