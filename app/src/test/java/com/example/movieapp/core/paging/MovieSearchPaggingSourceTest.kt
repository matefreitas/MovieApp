package com.example.movieapp.core.paging

import androidx.compose.runtime.key
import androidx.paging.PagingSource
import com.example.movieapp.TestDispatcherRule
import com.example.movieapp.core.domain.model.MovieSearch
import com.example.movieapp.core.domain.model.MovieSearchFactory
import com.example.movieapp.core.domain.model.MovieSearchPagingFactory
import com.example.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
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
class MovieSearchPaggingSourceTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var remoteDataSource: MovieSearchRemoteDataSource

    private val movieSearchFactory = MovieSearchFactory()

    private val movieSearchPagingFactory = MovieSearchPagingFactory().create()

    private val movieSearchPagingSource by lazy {
        MovieSearchPaggingSource(query = "", remoteDataSource = remoteDataSource)
    }

    @Test
    fun `must return a sucess load result when load it is called`() = runTest {
        whenever(remoteDataSource.getSearchMovies(any(), any())).thenReturn(
            movieSearchPagingFactory
        )

        val result = movieSearchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        val resultExpected = listOf(
            movieSearchFactory.create(MovieSearchFactory.Poster.Avengers),
            movieSearchFactory.create(MovieSearchFactory.Poster.JohnWick)
        )

        assertThat(PagingSource.LoadResult.Page(
            data = resultExpected,
            prevKey = null,
            nextKey = null
        )).isEqualTo(result)
    }

    @Test
    fun `must return a error load result when load is calles`() = runTest {
        val expectation = RuntimeException()
        whenever(remoteDataSource.getSearchMovies(any(), any())).thenThrow(
            expectation
        )

        val result = movieSearchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertThat(PagingSource.LoadResult.Error<Int, MovieSearch>(expectation)).isEqualTo(result)
    }

}