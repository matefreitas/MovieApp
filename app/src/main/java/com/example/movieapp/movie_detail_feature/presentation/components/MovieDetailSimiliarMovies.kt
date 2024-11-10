package com.example.movieapp.movie_detail_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.presentation.components.commom.ErrorScreen
import com.example.movieapp.core.presentation.components.commom.LoadingView
import com.example.movieapp.movie_popular_feature.presentation.components.MovieItem

@Composable
fun MovieDetailSimiliarMovies(
    modifier: Modifier = Modifier,
    pagingMoviesSimilar: LazyPagingItems<Movie>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        items(pagingMoviesSimilar.itemCount){ index ->
            val movie = pagingMoviesSimilar[index]
            movie?.let {
                MovieItem(
                    id = it.id,
                    voteAverage = it.voteAverage,
                    imageUrl = it.imageUrl,
                    onclick = {}
                )
            }
        }
        pagingMoviesSimilar.apply {
            when{
                loadState.refresh is LoadState.Loading -> {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){
                        LoadingView()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){
                        LoadingView()
                    }
                }
                loadState.refresh is LoadState.Error ->{
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){
                        ErrorScreen(
                            message = "Verifique sua conexão com a internet",
                            retry = {
                                retry()
                            }
                        )
                    }
                }
                loadState.append is LoadState.Error ->{
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){
                        ErrorScreen(
                            message = "Verifique sua conexão com a internet",
                            retry = {
                                retry()
                            }
                        )
                    }
                }
            }
        }
    }
}