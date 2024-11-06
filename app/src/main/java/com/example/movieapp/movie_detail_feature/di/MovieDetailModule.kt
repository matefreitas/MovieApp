package com.example.movieapp.movie_detail_feature.di

import com.example.movieapp.core.data.remote.MovieService
import com.example.movieapp.movie_detail_feature.data.repository.MovieDetailsRepositoryImpl
import com.example.movieapp.movie_detail_feature.data.source.MovieDetailsRemoteDataSourceImpl
import com.example.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import com.example.movieapp.movie_detail_feature.domain.source.MovieDetailsRemoteDataSource
import com.example.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import com.example.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {

    @Provides
    @Singleton
    fun provideMovieDetailsDataSource(service: MovieService): MovieDetailsRemoteDataSource{
        return MovieDetailsRemoteDataSourceImpl(service = service)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(remoteDataSource: MovieDetailsRemoteDataSource): MovieDetailsRepository{
        return MovieDetailsRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(repository: MovieDetailsRepository): GetMovieDetailsUseCase{
        return GetMovieDetailsUseCaseImpl(repository = repository)
    }
}