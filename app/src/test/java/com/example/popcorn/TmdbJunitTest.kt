package com.example.popcorn

import com.example.popcorn.api.TmdbApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TmdbApiJunitTest {

    private val apiKey = "your-api-key"
    private val genreId = 28

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val tmdbApi = retrofit.create(TmdbApi::class.java)

    @Test
    fun testErrorResponse() = runBlocking {
        val response = tmdbApi.getMoviesByGenre(apiKey, genreId)

        if (response.isSuccessful) {
            println("The response is successful. The API key is valid.")
            assertNotNull(response.body(), "Response body is null")
            val movies = response.body()?.results
            assertTrue(!movies.isNullOrEmpty(), "Movies list is empty")
        } else {
            println("The response is not successful. This may indicate an invalid API key.")
            assertNotNull(response.errorBody(), "Error body is null")
            println(response.errorBody()?.string())
        }
    }


    @Test
    fun testMovieResponseFields() = runBlocking {
        val response = tmdbApi.getMoviesByGenre(apiKey, genreId)

        assertTrue(response.isSuccessful, "Response is not successful")
        assertNotNull(response.body(), "Response body is null")
        val movies = response.body()!!.results
        assertTrue(movies.isNotEmpty(), "Movies list is empty")

        // Alanların eksik olup olmadığını kontrol et
        movies.forEach { movie ->
            assertNotNull(movie.id, "Movie id is null")
            assertNotNull(movie.title, "Movie title is null")
            assertNotNull(movie.overview, "Movie overview is null")
        }
    }


    @Test
    fun testGenreResponseTypes() = runBlocking {
        val response = tmdbApi.getGenres(apiKey)

        assertTrue(response.isSuccessful, "Response is not successful")
        assertNotNull(response.body(), "Response body is null")
        val genres = response.body()!!.genres
        assertTrue(genres.isNotEmpty(), "Genres list is empty")

        genres.forEach { genre ->
            assertNotNull(genre.id, "Genre id is null")
            assertNotNull(genre.name, "Genre name is null")
            assertTrue(genre.id is Int, "Genre id is not an integer")
            assertTrue(genre.name is String, "Genre name is not a string")
        }
    }
}
