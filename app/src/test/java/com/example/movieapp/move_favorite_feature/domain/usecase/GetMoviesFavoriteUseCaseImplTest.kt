package com.example.movieapp.move_favorite_feature.domain.usecase

import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieFactory
import com.example.movieapp.move_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMoviesFavoriteUseCaseImplTest{
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movies = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
    )

    private val getMoviesFavoriteUseCase by lazy {
        GetMoviesFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `should return success resultStatus when the repository returns list`() = runTest {
        whenever(movieFavoriteRepository.getMovies()).thenReturn(
            flowOf(
                movies
            )
        )

        val result = getMoviesFavoriteUseCase.invoke().first()

        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result).contains(movies[1])
    }

    @Test
    fun `should emit empty stream when exception is thrown when calling the invoke method`() = runTest {
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.getMovies()).thenThrow(exception)

        val result = getMoviesFavoriteUseCase.invoke().toList()

        verify(movieFavoriteRepository).getMovies()

        Truth.assertThat(result).isEmpty()
    }
}