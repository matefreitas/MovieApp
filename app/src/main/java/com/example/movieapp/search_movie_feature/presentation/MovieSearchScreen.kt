package com.example.movieapp.search_movie_feature.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.R
import com.example.movieapp.search_movie_feature.presentation.components.SearchContent
import com.example.movieapp.search_movie_feature.presentation.state.MovieSearchState
import com.example.movieapp.ui.theme.white


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
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_movies),
                        color = white
                    )
                }
            )
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