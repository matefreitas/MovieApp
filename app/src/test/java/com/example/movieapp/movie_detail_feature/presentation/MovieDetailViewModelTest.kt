package com.example.movieapp.movie_detail_feature.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieFactory
import com.example.movieapp.core.domain.model.MoviesDetailsFactory
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.move_favorite_feature.domain.usecase.AddMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.domain.usecase.DeleteMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.domain.usecase.IsMovieFavoriteUseCase
import com.example.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMoviesDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var addMovieFavoriteUseCase: AddMovieFavoriteUseCase

    @Mock
    lateinit var deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase

    @Mock
    lateinit var isMovieFavorite: IsMovieFavoriteUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private val moviesDetailsFactory = MoviesDetailsFactory().create(
        poster = MoviesDetailsFactory.Poster.Avengers
    )

    private val pagingData = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
        )
    )

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val viewModel by lazy {
        MovieDetailViewModel(
            getMoviesDetailsUseCase,
            addMovieFavoriteUseCase,
            deleteMovieFavoriteUseCase,
            isMovieFavorite,
            savedStateHandle.apply {
                whenever(savedStateHandle.get<Int>("movieId")).thenReturn(movie.id)
            }
        )
    }
/*
    @Test
    fun `must notify uiState with Success when get movies similar and movie details returns success`() = runTest {
        whenever(getMoviesDetailsUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(flowOf(pagingData) to moviesDetailsFactory))
        )

        val argumetCaptor = argumentCaptor<GetMovieDetailsUseCase.Params>()

        viewModel.uiState.isLoading

        verify(getMoviesDetailsUseCase).invoke(argumetCaptor.capture())
        assertThat(moviesDetailsFactory.id).isEqualTo(argumetCaptor.firstValue.movieId)
        val moviesDetails = viewModel.uiState.movieDetails
        val results = viewModel.uiState.results
        assertThat(moviesDetails).isNotNull()
        assertThat(results).isNotNull()
    }

    @Test
    fun `must notify uiState Error when get movies and returns exception`() = runTest {
        val exception = Exception("Um erro ocorreu")
        whenever(getMoviesDetailsUseCase.invoke(any())).thenReturn(flowOf(ResultData.Failure(exception)))

        viewModel.uiState.isLoading

        val error = viewModel.uiState.error

        assertThat(exception.message).isEqualTo(error)
    }

    @Test
    fun `must call delete favorite and notify of uiState with filed favorite icon when current icon is checked`() = runTest {
        whenever(deleteMovieFavoriteUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(Unit))
        )
        whenever(isMovieFavorite.invoke(any())).thenReturn(
            flowOf(ResultData.Success(true))
        )

        val deleteArgumentCaptor = argumentCaptor<DeleteMovieFavoriteUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.onAddFavorite(movie)

        verify(deleteMovieFavoriteUseCase).invoke(deleteArgumentCaptor.capture())
        assertThat(movie).isEqualTo(deleteArgumentCaptor.firstValue.movie)

        verify(isMovieFavorite).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.White).isEqualTo(iconColor)
    }

    @Test
    fun `must notify uiState with filled favorite icon when current icon is unchecked`() = runTest {
        whenever(addMovieFavoriteUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(Unit))
        )
        whenever(isMovieFavorite.invoke(any())).thenReturn(
            flowOf(ResultData.Success(false))
        )

        val addArgumentCaptor = argumentCaptor<AddMovieFavoriteUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.onAddFavorite(movie)

        verify(addMovieFavoriteUseCase).invoke(addArgumentCaptor.capture())
        assertThat(movie).isEqualTo(addArgumentCaptor.firstValue.movie)

        verify(isMovieFavorite).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.Red).isEqualTo(iconColor)
    }

    @Test
    fun `must notify uiState with bookmark icon filled in check returns true`() = runTest {
        whenever(isMovieFavorite.invoke(any())).thenReturn(
            flowOf(ResultData.Success(true))
        )

        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.uiState.isLoading

        verify(isMovieFavorite).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.Red).isEqualTo(iconColor)
    }

    @Test
    fun `must notify uiState with bookmark icon filled in check returns false`() = runTest {
        whenever(isMovieFavorite.invoke(any())).thenReturn(
            flowOf(ResultData.Success(false))
        )

        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.uiState.isLoading

        verify(isMovieFavorite).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.White).isEqualTo(iconColor)
    }*/
}