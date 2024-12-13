package com.example.movieapp.movie_detail_feature.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieFactory
import com.example.movieapp.core.domain.model.MoviesDetailsFactory
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.move_favorite_feature.data.usecase.AddMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.DeleteMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.IsMovieFavoriteUseCase
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
}