package com.example.popcorn.api

import com.example.popcorn.api.response.TrendingMoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingMovieApi {
    @GET("trending/movie/day") // Günlük trend filmler
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): TrendingMoviesResponse
}

val trendingApi = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(TrendingMovieApi::class.java)


