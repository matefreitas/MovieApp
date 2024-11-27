package com.example.movieapp.movie_popular_feature.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.R
import com.example.movieapp.core.presentation.components.commom.MovieAppBar
import com.example.movieapp.core.util.UtilFunctions
import com.example.movieapp.movie_popular_feature.presentation.components.MovieContent
import com.example.movieapp.movie_popular_feature.presentation.state.MoviePopularState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviePopularScreen(
    uiState: MoviePopularState,
    navigateToDetailMovie: (Int) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppBar(title = R.string.popular_movies)
        },
        content = {paddingValues ->
            MovieContent(
                pagingMovies = movies,
                paddingValues = paddingValues,
                onclick = {movieId ->
                    UtilFunctions.logInfo("MOVIE_ID", movieId.toString())
                    navigateToDetailMovie(movieId)
                }
            )
        },
    )
}