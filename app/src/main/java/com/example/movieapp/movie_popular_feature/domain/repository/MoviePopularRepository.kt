package com.example.movieapp.movie_popular_feature.domain.repository

import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie

interface MoviePopularRepository {

    fun getPopularMovies(): PagingSource<Int, Movie>
}