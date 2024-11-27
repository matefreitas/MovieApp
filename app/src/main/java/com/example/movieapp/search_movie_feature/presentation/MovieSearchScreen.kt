package com.example.movieapp.search_movie_feature.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.R
import com.example.movieapp.core.presentation.components.commom.MovieAppBar
import com.example.movieapp.search_movie_feature.presentation.components.SearchContent
import com.example.movieapp.search_movie_feature.presentation.state.MovieSearchState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(
    uiState: MovieSearchState,
    onEvent: (MovieSearchEvent) -> Unit,
    onfetch: (String) -> Unit,
    navigateToDetailMovie: (Int) -> Unit
) {
    val pagingMovies = uiState.movies.collectAsLazyPagingItems()
    Scaffold (
        topBar = {
            MovieAppBar(title = R.string.search_movies)
        },
        content = {paddingValues ->
            SearchContent(
                modifier = Modifier.padding(paddingValues),
                paddingValues = paddingValues,
                pagingMovies = pagingMovies,
                query = uiState.query,
                onSearch = {
                    onfetch(it)
                },
                onEvent = {
                    onEvent(it)
                },
                onDetail = {movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}