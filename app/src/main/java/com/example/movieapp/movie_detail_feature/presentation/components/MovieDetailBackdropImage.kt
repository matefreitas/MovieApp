package com.example.movieapp.movie_detail_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.movieapp.R

@Composable
fun MovieDetailBackdropImage(
    modifier: Modifier = Modifier,
    backdropImageUrl: String
) {
    Box(modifier = modifier){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropImageUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error_image)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun MovieDetailBackdropImagePreview() {
    MovieDetailBackdropImage(
        backdropImageUrl = "",
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}