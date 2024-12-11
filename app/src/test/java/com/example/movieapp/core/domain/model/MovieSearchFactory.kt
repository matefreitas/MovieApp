package com.example.movieapp.core.domain.model

class MovieSearchFactory {
    fun create(poster: Poster) = when (poster){
        Poster.Avengers -> {
            MovieSearch(
                id = 1,
                voteAvarege = 7.1,
                imageUrl = "Url"
            )
        }
        Poster.JohnWick -> {
            MovieSearch(
                id = 2,
                voteAvarege = 7.9,
                imageUrl = "Url"
            )
        }
    }

    sealed class Poster{
        object Avengers: Poster()
        object JohnWick: Poster()
    }
}