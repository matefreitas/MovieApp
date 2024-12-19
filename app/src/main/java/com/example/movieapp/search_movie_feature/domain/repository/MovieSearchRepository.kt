package com.example.movieapp.search_movie_feature.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieSearch
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {
    fun getSearchMovies(query: String): PagingSource<Int, MovieSearch>
}