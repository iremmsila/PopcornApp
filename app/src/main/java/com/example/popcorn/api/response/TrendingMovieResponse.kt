package com.example.popcorn.api.response

data class TrendingMoviesResponse(
    val results: List<Movie>
)

data class TrendingMovie(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double
)
