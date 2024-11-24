package com.example.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieapp.core.util.Constants
import com.example.movieapp.move_favorite_feature.presentation.MovieFavoriteViewModel
import com.example.movieapp.move_favorite_feature.presentation.components.MovieFavoriteScreen
import com.example.movieapp.movie_detail_feature.presentation.MovieDetailScreen
import com.example.movieapp.movie_detail_feature.presentation.MovieDetailViewModel
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
                navigateToDetailMovie = {
                    navHostController.navigate(BottomNavItem.MovieDetail.passMovieId(movieId = it))
                }
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
                navigateToDetailMovie = {
                    navHostController.navigate(BottomNavItem.MovieDetail.passMovieId(movieId = it))
                }
            )
        }
        composable(BottomNavItem.MovieFavorite.route){
            val viewModel: MovieFavoriteViewModel = hiltViewModel()
            val uiState = viewModel.uiState

            MovieFavoriteScreen(
                uiState = uiState,
                navigateToDetail = {
                    navHostController.navigate(BottomNavItem.MovieDetail.passMovieId(movieId = it))
                }
            )
        }

        composable(
            route = BottomNavItem.MovieDetail.route,
            arguments = listOf(
                navArgument(Constants.MOVIE_DETAIL_ARGUMENT_KEY){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            val viewModel: MovieDetailViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val onAddFavorite  = viewModel::onAddFavorite

            MovieDetailScreen(
                uiState = uiState,
                onAddFavorite = onAddFavorite,
            )
        }
    }
}