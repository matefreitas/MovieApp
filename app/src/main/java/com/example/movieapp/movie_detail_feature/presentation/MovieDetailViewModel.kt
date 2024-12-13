package com.example.movieapp.movie_detail_feature.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.util.Constants
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.core.util.UtilFunctions
import com.example.movieapp.move_favorite_feature.data.usecase.AddMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.DeleteMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.IsMovieFavoriteUseCase
import com.example.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import com.example.movieapp.movie_detail_feature.presentation.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addMovieFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val isMovieFavorite: IsMovieFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(MovieDetailState())
        private set

    private val movieId = savedStateHandle.get<Int>(key = Constants.MOVIE_DETAIL_ARGUMENT_KEY)

    init {
        movieId?.let { safeMovieId ->
            //checkedFavorite(MovieDetailEvent.CheckedFavorite(safeMovieId))
            getMovieDetail(MovieDetailEvent.GetMovieDetail(safeMovieId))
        }
    }

    private fun checkedFavorite(checkedFavorite: MovieDetailEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    private fun getMovieDetail(getMovieDetail: MovieDetailEvent.GetMovieDetail) {
        event(getMovieDetail)
    }

    fun onAddFavorite(movie: Movie) {
        if (uiState.iconColor == Color.White) {
            event(MovieDetailEvent.AddFavorite(movie = movie))
        } else {
            event(MovieDetailEvent.RemoveFavorite(movie = movie))
        }
    }

    private fun event(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.AddFavorite -> {
                viewModelScope.launch {
                    addMovieFavoriteUseCase.invoke(
                        params = AddMovieFavoriteUseCase.Params(
                            movie = event.movie
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(iconColor = Color.Red)
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError("Detail", "Erro ao cadastrar filme")
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.CheckedFavorite -> {
                viewModelScope.launch {
                    isMovieFavorite.invoke(
                        params = IsMovieFavoriteUseCase.Params(
                            movieId = event.movieId
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = if (result.data == true) {
                                    uiState.copy(iconColor = Color.Red)
                                } else {
                                    uiState.copy(iconColor = Color.White)
                                }
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError("Detail", "Erro ocorreu")
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    deleteMovieFavoriteUseCase.invoke(
                        params = DeleteMovieFavoriteUseCase.Params(
                            movie = event.movie
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(iconColor = Color.White)
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError("Detail", "Erro ocorreu")
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.GetMovieDetail -> {
                viewModelScope.launch {
                    getMovieDetailsUseCase.invoke(
                        params = GetMovieDetailsUseCase.Params(
                            movieId = event.movieId
                        )
                    ).collect { resultData ->
                        when (resultData) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    movieDetails = resultData.data?.second,
                                    results = resultData.data?.first ?: emptyFlow()
                                )
                            }

                            is ResultData.Failure -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    error = resultData.e?.message.toString()
                                )
                                UtilFunctions.logError(
                                    "DETAIL-ERRO",
                                    resultData.e?.message.toString()
                                )
                            }

                            is ResultData.Loading -> {
                                uiState = uiState.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}