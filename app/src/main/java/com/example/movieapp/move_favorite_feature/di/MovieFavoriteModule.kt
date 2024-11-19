package com.example.movieapp.move_favorite_feature.di

import com.example.movieapp.core.data.local.dao.MovieDao
import com.example.movieapp.move_favorite_feature.data.repository.MovieFavoriteRepositoryImpl
import com.example.movieapp.move_favorite_feature.data.source.MovieFavoriteLocalDataSourceImpl
import com.example.movieapp.move_favorite_feature.data.usecase.AddMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.AddMovieFavoriteUseCaseImpl
import com.example.movieapp.move_favorite_feature.data.usecase.DeleteMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.DeleteMovieFavoriteUseCaseImpl
import com.example.movieapp.move_favorite_feature.data.usecase.GetMoviesFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.GetMoviesFavoriteUseCaseImpl
import com.example.movieapp.move_favorite_feature.data.usecase.IsMovieFavoriteUseCase
import com.example.movieapp.move_favorite_feature.data.usecase.IsMovieFavoriteUseCaseImpl
import com.example.movieapp.move_favorite_feature.domain.repository.MovieFavoriteRepository
import com.example.movieapp.move_favorite_feature.domain.source.MovieFavoriteLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieFavoriteModule {
    @Provides
    @Singleton
    fun provideMovieFavoriteLocalDataSource(dao: MovieDao): MovieFavoriteLocalDataSource{
        return MovieFavoriteLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideMovieFavoriteRepository(localDataSource: MovieFavoriteLocalDataSource): MovieFavoriteRepository{
        return MovieFavoriteRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): GetMoviesFavoriteUseCase{
        return GetMoviesFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Provides
    @Singleton
    fun provideAddMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): AddMovieFavoriteUseCase{
        return AddMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Provides
    @Singleton
    fun deleteMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): DeleteMovieFavoriteUseCase{
        return DeleteMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Provides
    @Singleton
    fun provideIsMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): IsMovieFavoriteUseCase{
        return IsMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }
}