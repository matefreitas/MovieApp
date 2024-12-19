package com.example.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieDetails
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(params: Params): ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>

    data class Params(val movieId: Int, val pagingConfig: PagingConfig)
}

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieDetailsRepository
) : GetMovieDetailsUseCase {
    override suspend fun invoke(params: GetMovieDetailsUseCase.Params): ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>> {
        return withContext(Dispatchers.IO){
            ResultData.Loading
            try {
                val pagingSource = repository.getMoviesSimilar(params.movieId)
                val movieDetails = repository.getMovieDetails(params.movieId)
                val pager = Pager(
                    config = params.pagingConfig,
                    pagingSourceFactory = {
                        pagingSource
                    }
                ).flow

                ResultData.Success(pager to movieDetails)
            }catch (e: Exception){
                ResultData.Failure(e)
            }
        }
    }
}