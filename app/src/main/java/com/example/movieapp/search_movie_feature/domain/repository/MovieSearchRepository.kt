package com.example.movieapp.search_movie_feature.domain.repository

import androidx.paging.PagingSource
import com.example.movieapp.core.domain.model.MovieSearch

interface MovieSearchRepository {
    fun getSearchMovies(query: String): PagingSource<Int, MovieSearch>
}