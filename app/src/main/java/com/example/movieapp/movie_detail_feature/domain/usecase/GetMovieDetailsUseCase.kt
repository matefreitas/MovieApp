package com.example.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.MovieDetails
import com.example.movieapp.core.util.ResultData
import com.example.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

interface GetMovieDetailsUseCase {
    operator fun invoke(params: Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>>

    data class Params(val movieId: Int)
}

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieDetailsRepository
) : GetMovieDetailsUseCase {
    override fun invoke(params: GetMovieDetailsUseCase.Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val movieDetails = repository.getMovieDetails(params.movieId)
                val movieSimilar = repository.getMoviesSimilar(
                    movieInt = params.movieId,
                    pagingConfig = PagingConfig(
                        pageSize = 20,
                        initialLoadSize = 20
                    )
                )
                emit(ResultData.Success(movieSimilar to movieDetails))
            } catch (e: HttpException) {
                emit(ResultData.Failure(e))
            } catch (e: IOException) {
                emit(ResultData.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}