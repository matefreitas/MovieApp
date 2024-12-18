package com.example.movieapp.move_favorite_feature.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.move_favorite_feature.domain.usecase.GetMoviesFavoriteUseCase
import com.example.movieapp.move_favorite_feature.presentation.state.MovieFavoriteState
import com.example.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel @Inject constructor(
    private val getMoviesFavoriteUseCase: GetMoviesFavoriteUseCase
): ViewModel() {
    var uiState by mutableStateOf(MovieFavoriteState())
        private set

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch{
            val movies = getMoviesFavoriteUseCase.invoke()
            uiState = uiState.copy(movies)
        }
    }
}