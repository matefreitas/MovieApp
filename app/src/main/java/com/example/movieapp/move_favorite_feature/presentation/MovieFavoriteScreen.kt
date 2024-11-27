package com.example.movieapp.move_favorite_feature.presentation

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.move_favorite_feature.presentation.state.MovieFavoriteState
import com.example.movieapp.R
import com.example.movieapp.core.presentation.components.commom.MovieAppBar
import com.example.movieapp.move_favorite_feature.presentation.components.MovieFavoriteContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFavoriteScreen(
    uiState: MovieFavoriteState,
    navigateToDetail: (Int) -> Unit
) {
    val movies = uiState.movies
    Scaffold(
        topBar = {
            MovieAppBar(title = R.string.favorite_movies)
        },
        content = {paddingValues ->
            MovieFavoriteContent(
                movies = movies,
                paddingValues = paddingValues,
                onClick = { movieId ->
                    navigateToDetail(movieId)
                }
            )
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun MovieFavoriteScreenPreview() {
    MovieFavoriteScreen(
        uiState = MovieFavoriteState(),
        navigateToDetail = {}
    )
}