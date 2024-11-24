package com.example.movieapp.movie_detail_feature.presentation

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
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.movie_detail_feature.presentation.components.MovieDetailContent
import com.example.movieapp.movie_detail_feature.presentation.state.MovieDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    uiState: MovieDetailState,
    onAddFavorite: (Movie) -> Unit,
) {
    val pagingMoviesSimilar = uiState.results.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.detail_movie))
                }
            )
        },
        content = {paddingValues ->
            MovieDetailContent(
                modifier = Modifier.padding(paddingValues),
                movieDetails = uiState.movieDetails,
                pagingMoviesSimilar = pagingMoviesSimilar,
                isLoading = uiState.isLoading,
                error = uiState.error,
                iconColor = uiState.iconColor,
                onAddFavorite = {
                    onAddFavorite(it)
                }
            )
        }
    )
}