package com.example.popcorn.api


import com.example.popcorn.api.response.GenresResponse
import com.example.popcorn.api.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): Response<GenresResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int
    ): Response<MovieResponse>

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}