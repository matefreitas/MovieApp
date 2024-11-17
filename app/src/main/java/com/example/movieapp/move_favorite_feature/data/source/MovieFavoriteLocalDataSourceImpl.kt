package com.example.movieapp.move_favorite_feature.data.source

import com.example.movieapp.core.data.local.dao.MovieDao
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.move_favorite_feature.data.mapper.toMovieEntity
import com.example.movieapp.move_favorite_feature.data.mapper.toMovies
import com.example.movieapp.move_favorite_feature.domain.source.MovieFavoriteLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieFavoriteLocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao
) : MovieFavoriteLocalDataSource {
    override fun getMovies(): Flow<List<Movie>> {
        return dao.getMovies().map {
            it.toMovies()
        }
    }

    override suspend fun insert(movie: Movie) {
        dao.insertMovie(movie.toMovieEntity())
    }

    override suspend fun delete(movie: Movie) {
        dao.deleteMovie(movie.toMovieEntity())
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return dao.isFavorite(movieId) != null
    }
}