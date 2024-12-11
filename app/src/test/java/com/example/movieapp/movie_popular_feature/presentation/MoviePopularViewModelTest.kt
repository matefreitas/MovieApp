package com.example.movieapp.movie_popular_feature.presentation

import androidx.paging.PagingData
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieFactory
import com.example.movieapp.movie_popular_feature.domain.usecase.GetPopularMoviesUseCase
import com.nhaarman.mockitokotlin2.whenever
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePopularViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private val viewModel by lazy {
        MoviePopularViewModel(getPopularMoviesUseCase)
    }

    private val fakePagingDataMovies = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
        )
    )

    @Test
    fun `must validate paging data object values when calling paging data from movies`() = runTest {
        whenever(getPopularMoviesUseCase.invoke()).thenReturn(flowOf(fakePagingDataMovies))

        val result = viewModel.uiState.movies.first()

        assertThat(result).isNotNull()
    }

    @Test(expected = RuntimeException::class)
    fun `must throw an exception when the calling to the use returns an exception`() = runTest {
        whenever(getPopularMoviesUseCase.invoke()).thenThrow(RuntimeException())

        val result = viewModel.uiState.movies.first()

        assertThat(result).isNull()
    }
}