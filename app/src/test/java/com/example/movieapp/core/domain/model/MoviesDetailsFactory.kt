package com.example.movieapp.core.domain.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

class MoviesDetailsFactory {
    fun create(poster: Poster) = when (poster){
        Poster.Avengers -> {
            MovieDetails(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                genres = listOf("Aventura", "Ação", "Ficção"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "Url",
                releaseDate = "12/12/2024",
                duration = 143,
                voteCount = 7
            )
        }
        Poster.JohnWick -> {
            MovieDetails(
                id = 2,
                title = "JohnWick",
                voteAverage = 7.9,
                genres = listOf("Aventura", "Ação", "Ficção"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "Url",
                releaseDate = "12/12/2024",
                duration = 143,
                voteCount = 7
            )
        }
    }

    sealed class Poster{
        object Avengers: Poster()
        object JohnWick: Poster()
    }
}