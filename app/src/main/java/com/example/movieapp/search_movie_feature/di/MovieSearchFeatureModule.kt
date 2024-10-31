package com.example.movieapp.search_movie_feature.di

import com.example.movieapp.core.data.remote.MovieService
import com.example.movieapp.search_movie_feature.data.repository.MovieSearchRepositoryImpl
import com.example.movieapp.search_movie_feature.data.source.MovieSearchRemoteDataSourceImpl
import com.example.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import com.example.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import com.example.movieapp.search_movie_feature.domain.usecase.GetMovieSearchUseCase
import com.example.movieapp.search_movie_feature.domain.usecase.GetMovieSearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieSearchFeatureModule {

    @Provides
    @Singleton
    fun provideMovieSearchDataSource(service: MovieService): MovieSearchRemoteDataSource{
        return MovieSearchRemoteDataSourceImpl(service = service)
    }

    @Provides
    @Singleton
    fun provideMovieSearchRepository(remoteDataSource: MovieSearchRemoteDataSource): MovieSearchRepository{
        return MovieSearchRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieSearchUseCase(repository: MovieSearchRepository): GetMovieSearchUseCase{
        return GetMovieSearchUseCaseImpl(repository = repository)
    }
}