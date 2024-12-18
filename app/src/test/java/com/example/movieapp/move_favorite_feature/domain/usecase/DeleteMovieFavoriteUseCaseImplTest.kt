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
class DeleteMovieFavoriteUseCaseImplTest{
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val deleteMovieFavoriteUseCase by lazy {
        DeleteMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `should return Success from ResultStatus when repository return success equal to unit`() = runTest {
        whenever(movieFavoriteRepository.delete(any())).thenReturn(Unit)

        val result = deleteMovieFavoriteUseCase.invoke(
            params = DeleteMovieFavoriteUseCase.Params(
                movie = movie
            )
        ).first()

        Truth.assertThat(result).isEqualTo(ResultData.Success(Unit))
    }

    @Test
    fun `should return failure from ResultStatus when the repository throws an exception`() = runTest {
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.delete(any())).thenThrow(
            exception
        )

        val result = deleteMovieFavoriteUseCase.invoke(
            params = DeleteMovieFavoriteUseCase.Params(
                movie = movie
            )
        ).first()

        Truth.assertThat(result).isEqualTo(ResultData.Failure(exception))
    }
}