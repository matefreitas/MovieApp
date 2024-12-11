package com.example.movieapp.search_movie_feature.presentation

import androidx.paging.PagingData
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieSearchFactory
import com.example.movieapp.search_movie_feature.domain.usecase.GetMovieSearchUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieSearchViewModelTest {
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getSearchMoviesUseCase: GetMovieSearchUseCase

    private val viewModel by lazy {
        MovieSearchViewModel(getSearchMoviesUseCase)
    }

    private val fakePagingDataSearchMovies = PagingData.from(
        listOf(
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers),
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.JohnWick)
        )
    )

    @Test
    fun `must validate paging data object values when calling movie search paging data`() = runTest {
        whenever(getSearchMoviesUseCase.invoke(any())).thenReturn(
            flowOf(fakePagingDataSearchMovies)
        )

        viewModel.fetch("")
        val result = viewModel.uiState.movies.first()

        assertThat(result).isNotNull()
    }

    @Test
    fun `must validate event `() = runTest {
        viewModel.event(MovieSearchEvent.EnteredQuery("test"))

        assertThat(viewModel.uiState.query).isEqualTo("test")
    }

    @Test(expected = RuntimeException::class)
    fun `must throw an exception when the calling to the use returns an exception`() = runTest {
        whenever(getSearchMoviesUseCase.invoke(any())).thenThrow(
            RuntimeException()
        )

        viewModel.fetch("")

    }
}