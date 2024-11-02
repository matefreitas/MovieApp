package com.example.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.movie_popular_feature.presentation.MoviePopularScreen
import com.example.movieapp.movie_popular_feature.presentation.MoviePopularViewModel
import com.example.movieapp.search_movie_feature.presentation.MovieSearchEvent
import com.example.movieapp.search_movie_feature.presentation.MovieSearchScreen
import com.example.movieapp.search_movie_feature.presentation.MovieSearchViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.MoviePopular.route
    ){
        composable(BottomNavItem.MoviePopular.route){
            val viewModel: MoviePopularViewModel = hiltViewModel()
            MoviePopularScreen(
                uiState = viewModel.uiState,
                navigateToDetailMovie = {}
            )
        }
        composable(BottomNavItem.MovieSearch.route){
            val viewModel: MovieSearchViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val onEvent: (MovieSearchEvent) -> Unit = viewModel::event
            val onfetch: (String) -> Unit = viewModel::fetch
            MovieSearchScreen(
                uiState = uiState,
                onEvent = onEvent,
                onfetch = onfetch,
                navigateToDetailMovie = {}
            )
        }
        composable(BottomNavItem.MovieFavorite.route){

        }
    }
}