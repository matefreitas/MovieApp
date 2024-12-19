package com.example.movieapp.move_favorite_feature.domain.usecase

import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieFactory
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.move_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class IsMovieFavoriteUseCaseImplTest{
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val isMovieFavorite by lazy {
        IsMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `must return Success from ResultStatus when the repository returns success with the value equal to true`() = runTest{
        whenever(movieFavoriteRepository.isFavorite(any())).thenReturn(
            true
        )

        val result = isMovieFavorite.invoke(
            params = IsMovieFavoriteUseCase.Params(
                movieId = movie.id
            )
        ).first()

        Truth.assertThat(result).isEqualTo(ResultData.Success(true))
    }

    @Test
    fun `must return Success from ResultStatus when the repository returns success with the value equal to false`() = runTest{
        whenever(movieFavoriteRepository.isFavorite(any())).thenReturn(
            false
        )

        val result = isMovieFavorite.invoke(
            params = IsMovieFavoriteUseCase.Params(
                movieId = movie.id
            )
        ).first()


        Truth.assertThat(result).isEqualTo(ResultData.Success(false))
    }

    @Test
    fun `must return Failure from ResultStatus when the repository returns throws an exception`() = runTest {
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.isFavorite(any())).thenThrow(
            exception
        )

        val result = isMovieFavorite.invoke(
            params = IsMovieFavoriteUseCase.Params(
                movieId = movie.id
            )
        ).first()

        Truth.assertThat(result).isEqualTo(ResultData.Failure(exception))
    }
}