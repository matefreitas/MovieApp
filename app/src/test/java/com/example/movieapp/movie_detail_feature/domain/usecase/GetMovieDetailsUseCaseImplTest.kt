package com.example.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.PagingConfig
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieFactory
import com.example.movieapp.core.domain.model.MoviesDetailsFactory
import com.example.movieapp.core.domain.model.PagingSourceMoviesFactory
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseImplTest{
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieDetailsRepository: MovieDetailsRepository

    private val movieFactory = MovieFactory().create(MovieFactory.Poster.Avengers)

    private val movieDetailFactory = MoviesDetailsFactory().create(MoviesDetailsFactory.Poster.Avengers)

    private val pagingSourceFake = PagingSourceMoviesFactory().create(listOf(movieFactory))

    private  val getMovieDetailsUseCase by lazy {
        GetMovieDetailsUseCaseImpl(movieDetailsRepository)
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() = runTest {
        whenever(movieDetailsRepository.getMovieDetails(any())).thenReturn(
            movieDetailFactory
        )
        whenever(movieDetailsRepository.getMoviesSimilar(any())).thenReturn(
            pagingSourceFake
        )

        val result = getMovieDetailsUseCase.invoke(
            params = GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(
                    initialLoadSize = 20,
                    pageSize = 20
                )
            )
        )

        verify(movieDetailsRepository).getMovieDetails(movieFactory.id)
        verify(movieDetailsRepository).getMoviesSimilar(movieFactory.id)
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result is ResultData.Success).isTrue()
    }

    @Test
    fun `should return Error from ResultStatus when get movieDetails request returns error`() = runTest {
        val exception = RuntimeException()
        whenever(movieDetailsRepository.getMovieDetails(any())).thenThrow(
            exception
        )

        val result = getMovieDetailsUseCase.invoke(
            params = GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(
                    initialLoadSize = 20,
                    pageSize = 20
                )
            )
        )

        verify(movieDetailsRepository).getMovieDetails(movieFactory.id)
        Truth.assertThat(result is ResultData.Failure).isTrue()

    }

    @Test
    fun `should return a ResultStatus error when getting similar movies returns an error`() = runTest {
        val exception = RuntimeException()
        whenever(movieDetailsRepository.getMoviesSimilar(any())).thenThrow(
            exception
        )

        val result = getMovieDetailsUseCase.invoke(
            params = GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(
                    initialLoadSize = 20,
                    pageSize = 20
                )
            )
        )

        verify(movieDetailsRepository).getMoviesSimilar(movieFactory.id)
        Truth.assertThat(result is ResultData.Failure).isTrue()

    }
}