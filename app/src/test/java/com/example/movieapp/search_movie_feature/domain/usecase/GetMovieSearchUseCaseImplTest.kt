package com.example.movieapp.search_movie_feature.domain.usecase

import androidx.paging.PagingConfig
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieSearchFactory
import com.example.movieapp.core.domain.model.PagingSourceMoviesSearchFactory
import com.example.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieSearchUseCaseImplTest{
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieSearchRepository: MovieSearchRepository

    private val movieSearchFactory = MovieSearchFactory().create(MovieSearchFactory.Poster.Avengers)

    private val pagingSourceFake = PagingSourceMoviesSearchFactory().create(listOf(movieSearchFactory))

    private val getMovieSearchUseCase by lazy {
        GetMovieSearchUseCaseImpl(movieSearchRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() = runTest {
        whenever(movieSearchRepository.getSearchMovies(any())).thenReturn(
            pagingSourceFake
        )

        val result = getMovieSearchUseCase.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        ).first()

        verify(movieSearchRepository).getSearchMovies("")
        Truth.assertThat(result).isNotNull()
    }

    @Test
    fun `should emit empty stream when a exception is throw when calling the method invoke`() = runTest {
        val exception = RuntimeException()
        whenever(movieSearchRepository.getSearchMovies(any())).thenThrow(
            exception
        )

        val result = getMovieSearchUseCase.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        ).toList()

        verify(movieSearchRepository).getSearchMovies("")
        Truth.assertThat(result).isEmpty()
    }

}