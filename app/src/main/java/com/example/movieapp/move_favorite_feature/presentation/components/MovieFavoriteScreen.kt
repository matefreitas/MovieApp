package com.example.movieapp.move_favorite_feature.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.movieapp.move_favorite_feature.presentation.state.MovieFavoriteState
import com.example.movieapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFavoriteScreen(
    uiState: MovieFavoriteState,
    navigateToDetail: (Int) -> Unit
) {
    val movies = uiState.movies
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorite_movies),
                    )
                }
            )
        },
        content = {paddingValues ->
            MovieFavoriteContent(
                movies = movies,
                paddingValues = paddingValues,
                onClick = {movieId ->
                    navigateToDetail(movieId)
                }
            )
        }
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO or android.content.res.Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun MovieFavoriteScreenPreview() {
    MovieFavoriteScreen(
        uiState = MovieFavoriteState(),
        navigateToDetail = {}
    )
}