package com.example.movieapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.movieapp.core.data.local.MovieDatabase
import com.example.movieapp.core.data.local.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class MovieDaoTest {

    @get:Rule
    var hiltRuler = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setup(){
        hiltRuler.inject()
        movieDao = database.movieDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun getMovies() = runTest {
        val movies = movieDao.getMovies().first()

        assertThat(movies.size).isEqualTo(0)
    }

    @Test
    fun getMovies_orderById() = runTest{
        val moviesEntitys = listOf(
            MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1"),
            MovieEntity(movieId = 4, title = "Homem de Ferro 2", imageUrl = "Url1"),
            MovieEntity(movieId = 2, title = "Homem de Ferro 3", imageUrl = "Url1"),
            MovieEntity(movieId = 3, title = "Homem de Ferro 4", imageUrl = "Url1")
        )
        movieDao.insertMovies(moviesEntitys)

        val movies = movieDao.getMovies().first()

        assertThat(movies.size).isEqualTo(4)
        assertThat(movies[0].movieId).isEqualTo(1)
        assertThat(movies[1].movieId).isEqualTo(2)
        assertThat(movies[2].movieId).isEqualTo(3)
        assertThat(movies[3].movieId).isEqualTo(4)
    }
}